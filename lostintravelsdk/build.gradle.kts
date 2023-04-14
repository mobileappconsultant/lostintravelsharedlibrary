import org.jetbrains.kotlin.gradle.plugin.mpp.apple.XCFramework
import java.util.Properties
import java.io.ByteArrayOutputStream


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
/*version = "0.0.7-ALPHA"*/
//version = versionManger.versionToString()

val githubProperties = Properties()
githubProperties.load(project.rootProject.file("github.properties").inputStream())


val libName = "lostintravelsdk"
val frameworkFileName = "$libName.xcframework"
val githubRepo = "https://github.com/mobileappconsultant/lostintravelsharedlibrary"

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

tasks.create("writeIOSPackageManifest") {
    dependsOn("zipIOSArtifacts")

    doLast {
        val packageManifest =
            """
// swift-tools-version:5.3
import PackageDescription

let package = Package(
    name: "${project.name}",
    platforms: [
        .iOS(.v13)
    ],
    products: [
        // Products define the executables and libraries a package produces, and make them visible to other packages.
        .library(
            name: "$libName",
            targets: ["$libName"])
    ],
    dependencies: [
        // Dependencies declare other packages that this package depends on.
    ],
    targets: [
        // Targets are the basic building blocks of a package. A target can define a module or a test suite.
        // Targets can depend on other targets in this package, and on products in packages this package depends on.
        .binaryTarget(
            name: "$libName",
            url: "$githubRepo/raw/$BRANCH_NAME/$libName.xcframework.zip",
            checksum: "<CHECKSUM_HERE>"
        ),
    ]
)
"""

        file("../Package.swift").writeText(packageManifest)

        ByteArrayOutputStream().let {
            exec {
                commandLine("swift", "package", "compute-checksum", "../${frameworkFileName}.zip")
                standardOutput = it
            }

            val checksum = it.toString().replace("\n", "")
            val finalManifest = packageManifest.replace("<CHECKSUM_HERE>", checksum)

            file("../Package.swift").writeText(finalManifest)
        }
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

tasks.create("packageiOSFramework") {
    println("Initializing packageiOSFramework...")

    val tempCapFirstChar = libName.replaceFirst(libName.first(), libName.first().toUpperCase())
    dependsOn("assemble${tempCapFirstChar}ReleaseXCFramework", "zipIOSArtifacts", "writeIOSPackageManifest")
}

tasks.create("pushToGit") {
    dependsOn("VersionBump", "packageiOSFramework", "publish")

    doLast {
        exec {
            commandLine("git", "add", "..")
        }
        exec {
            commandLine("git", "commit", "-m", "published new library version $version")
        }
        exec {
            commandLine("git", "push")
        }
    }
}
tasks.create("VersionBump") {
    doLast {
        val versionManger = VersionManager(File("${projectDir.absolutePath}/VERSION"))
        versionManger.bumpCode()
        versionManger.saveVersionString()
        project.version = versionManger.versionToString()
    }
}
tasks.create("publishEverything") {
    dependsOn("pushToGit")
}
