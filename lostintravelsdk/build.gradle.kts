import org.jetbrains.kotlin.gradle.plugin.mpp.apple.XCFramework
import tasks.PushToGitTask
import tasks.VersionBumpTask
import tasks.VersionBumpType
import tasks.IOSFrameworkPublisher
import java.util.Properties


val projectProperties = Properties().apply {
    load(project.rootProject.file("project.properties").inputStream())
}



val PACKAGE_NAMESPACE = projectProperties["project_namespace"] as String? ?: System.getenv("PROJECT_NAMESPACE")
val MASTER_BRANCH = projectProperties["master_branch"] as String? ?: System.getenv("MASTER_BRANCH")
val ARTIFACTS_BRANCH = projectProperties["artifacts"] as String? ?: System.getenv("ARTIFACTS_BRANCH")
val GRAPHQL_BASE_URL = projectProperties["base_api_url"] as String? ?: System.getenv("SERVER_BASE_URL")
val GRAPHQL_SCHEMA_FILE = projectProperties["graphql_schema"] as String? ?: System.getenv("GRAPHQL_SCHEMA")
val REPO_PATH = projectProperties["repo_path"] as String? ?: System.getenv("REPO_PATH")
val USERNAME = projectProperties["gpr.user"] as String? ?: System.getenv("GPR_USER")
val PASSWORD = projectProperties["gpr.key"] as String? ?: System.getenv("GPR_KEY")


plugins {
    kotlin(Plugins.multiplatform)
    id(Plugins.library)
    id(Plugins.maven_publish)
    id(Plugins.graphQL)
    id(Plugins.sqlDelight)
    id(Plugins.kotlinx_serialization)
    kotlin(Plugins.serialization) version Kotlin.version
}

group = PACKAGE_NAMESPACE

val libraryName = "lostintravelsdk"
val frameworkFileName = "$libraryName.xcframework"
val repositoryPath = "https://github.com/mobileappconsultant/lostintravelsharedlibrary"


kotlin {
    android {
        publishLibraryVariants("release", "debug")
        publishLibraryVariantsGroupedByFlavor = true


        compilations.all {
            kotlinOptions {
                jvmTarget = "11"
            }
        }
    }


    val xcFramework = XCFramework()

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            xcFramework.add(this)
            binaryOption("bundleId", PACKAGE_NAMESPACE)
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(GraphQl.graphQlDep)
                implementation(SQLDelight.runtime)
                implementation(SQLDelight.coroutines)
                implementation(Ktor.core)
                implementation(Ktor.logging)
                implementation(Ktor.clientSerialization)
                implementation(Ktor.content_negotiation)
                implementation(Ktor.ktor_json_serialization)
            }
        }

        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        val androidMain by getting {
            dependencies {
                implementation(SQLDelight.androidDriver)
                implementation(Ktor.android)
                implementation(Ktor.okhttp)
                implementation(Ktor.okhttpLogging)
            }
        }
        val androidUnitTest by getting
        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting
        val iosMain by creating {
            dependsOn(commonMain)
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)
            dependencies {
                implementation(Ktor.ktor_darwin)
                implementation(SQLDelight.nativeDriver)
            }
        }
        val iosX64Test by getting
        val iosArm64Test by getting
        val iosSimulatorArm64Test by getting
        val iosTest by creating {
            dependsOn(commonTest)
            iosX64Test.dependsOn(this)
            iosArm64Test.dependsOn(this)
            iosSimulatorArm64Test.dependsOn(this)
        }
    }
}

android {
    namespace = PACKAGE_NAMESPACE
    compileSdk = 33
    defaultConfig {
        minSdk = 24
        targetSdk = 33
    }
}


publishing {
    repositories {
        maven {
            name = "GitHubPackages"
            url = uri(repositoryPath)
            credentials {
                username = USERNAME
                password = PASSWORD
            }
        }
    }
}


configure<com.apollographql.apollo3.gradle.api.ApolloExtension> {

    sourceSets {
        schemaFile.set(file(GRAPHQL_SCHEMA_FILE))
    }

}

apollo {
    packageName.set(PACKAGE_NAMESPACE)
    generateKotlinModels.set(true)

    introspection {
        schemaFile.set(File(GRAPHQL_SCHEMA_FILE))
        endpointUrl.set("${GRAPHQL_BASE_URL}graphql")
    }
}



sqldelight {
    database("LostInTravelDatabase") {
        packageName = "$PACKAGE_NAMESPACE.datasource.cache"
        sourceFolders = listOf("sqldelight")
    }
}

tasks.create<Zip>("zipIOSArtifacts") {
    // Zip file name
    archiveFileName.set("${frameworkFileName}.zip")

    // Destination for Zip file
    destinationDirectory.set(layout.projectDirectory.dir("../"))

    // Source directory for ZIP
    from("${buildDir.absolutePath}/XCFrameworks/release")
}

tasks.register<PushToGitTask>("pushToGit") {
    originalBranch.set(MASTER_BRANCH)
    branch.set(ARTIFACTS_BRANCH)
    commitMessage.set("Updated new library version to $version")
    filesToCommit.set(listOf("-A"))
    extraPushArgs.set(listOf("origin", ARTIFACTS_BRANCH))
    artifactsToTransfer.set(listOf(
        File(projectDir, "../$frameworkFileName.zip").absolutePath,
        File(projectDir, "../Package.swift").absolutePath,
    ))
}

tasks.register<VersionBumpTask>("versionBump") {
    file.set(File("${projectDir.absolutePath}/VERSION"))
    bumpType.set(VersionBumpType.CODE)

    doLast {
        exec {
            commandLine("git", "add", ".")
        }
        exec {
            commandLine("git", "commit", "-m", "\"Bumped version to ${version}\"")
        }
    }
}

tasks.register<IOSFrameworkPublisher>("assembleIOSFramework") {
    val tempCapFirstChar = libraryName.replaceFirst(libraryName.first(), libraryName.first().toUpperCase())

    val assembleDep = "assemble${tempCapFirstChar}ReleaseXCFramework"
    val zipDep = "zipIOSArtifacts"

    dependsOn(assembleDep, zipDep)

    tasks.named(zipDep).get().mustRunAfter(tasks.named(assembleDep).get())

    libName.set(libraryName)
    githubRepo.set("https://github.com/mobileappconsultant/lostintravelsharedlibrary")
    branchName.set(ARTIFACTS_BRANCH)
    archivePath.set("../$libraryName.xcframework.zip")
    packageFile.set(project.file("../Package.swift"))
}

tasks.create("publishIOSFramework") {
    val assembleDep = "assembleIOSFramework"
    val pushToGitDep = "pushToGit"
    val versionBump = "versionBump"
    dependsOn(versionBump, assembleDep, pushToGitDep)

    tasks.named(assembleDep).get().mustRunAfter(tasks.named(versionBump).get())
    tasks.named(pushToGitDep).get().mustRunAfter(tasks.named(assembleDep).get())
}

tasks.create("publishEverything") {
    val publishIOS = "publishIOSFramework"
    val publish = "publish"
    dependsOn(publishIOS, publish)

    tasks.named(publish).get().mustRunAfter(tasks.named(publishIOS).get())
}
