import org.jetbrains.kotlin.gradle.plugin.mpp.apple.XCFramework
import tasks.PushToGitTask
import tasks.VersionBumpTask
import tasks.VersionBumpType
import tasks.IOSFrameworkPublisher
import java.util.Properties


val PACKAGE_NAMESPACE = "com.arkangel.lostintravelsharedlibrary"
val BRANCH_NAME = "master"
val GRAPHQL_BASE_URL = "https://ldabs6rj.connect.remote.it/"
val GRAPHQL_SCHEMA_FILE = "src/commonMain/graphql/schema.json"

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

val githubProperties = Properties()
githubProperties.load(project.rootProject.file("github.properties").inputStream())


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
            url = uri("https://maven.pkg.github.com/mobileappconsultant/lostintravelsharedlibrary")
            credentials {
                username = githubProperties["gpr.user"] as String? ?: System.getenv("GPR_USER")
                password = githubProperties["gpr.key"] as String? ?: System.getenv("GPR_KEY")
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
/*    customTypeMapping.set(mapOf(
        "DateTime" to "java.util.Date",
        "Date" to "java.util.Date"
    ))*/

    introspection {
        schemaFile.set(File(GRAPHQL_SCHEMA_FILE))
        endpointUrl.set("${GRAPHQL_BASE_URL}graphql")
        /* headers.set(mapOf(
            "Authorization" to "Bearer your_auth_token"
        ))*/
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
    originalBranch.set("master")
    branch.set("artifacts")
    commitMessage.set("Updated new library version to $version")
    filesToCommit.set(listOf("-A"))
    extraPushArgs.set(listOf("origin", "artifacts"))
    artifactsToTransfer.set(listOf(
        File(projectDir, "../$frameworkFileName.zip").absolutePath,
        File(projectDir, "../Package.swift").absolutePath,
    ))
}

tasks.register<VersionBumpTask>("versionBump") {
    file.set(File("${projectDir.absolutePath}/VERSION"))
    bumpType.set(VersionBumpType.CODE)

    doLast {
        println("Telling gradle about the new version...")
        version = newVersion()
        println("Bumped version to $version")
    }
}

tasks.register<IOSFrameworkPublisher>("assembleIOSFramework") {
    val tempCapFirstChar = libraryName.replaceFirst(libraryName.first(), libraryName.first().toUpperCase())

    val assembleDep = "assemble${tempCapFirstChar}ReleaseXCFramework"
    val zipDep = "zipIOSArtifacts"

    dependsOn(assembleDep, zipDep)

    tasks.named(zipDep).get().mustRunAfter(tasks.named(assembleDep).get())

    libName.set(libraryName)
    githubRepo.set(repositoryPath)
    branchName.set("artifacts")
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
