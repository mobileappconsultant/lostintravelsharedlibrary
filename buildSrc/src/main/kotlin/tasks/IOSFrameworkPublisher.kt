package tasks

import org.gradle.api.DefaultTask
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction
import org.gradle.work.DisableCachingByDefault
import java.io.ByteArrayOutputStream
import java.io.File

@DisableCachingByDefault(because = "Not needed")
abstract class IOSFrameworkPublisher: DefaultTask() {

    @get:Input
    abstract val libName: Property<String>

    @get:Input
    abstract val githubRepo: Property<String>

    @get:Input
    abstract val branchName: Property<String>

    @get:Input
    abstract val archivePath: Property<String>

    @get:Input
    abstract val packageFile: Property<File>

    @TaskAction
    fun execute() {
        println("Assembling iOS XCFramework package...")

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
            name: "${libName.get()}",
            targets: ["${libName.get()}"])
    ],
    dependencies: [
        // Dependencies declare other packages that this package depends on.
    ],
    targets: [
        // Targets are the basic building blocks of a package. A target can define a module or a test suite.
        // Targets can depend on other targets in this package, and on products in packages this package depends on.
        .binaryTarget(
            name: "${libName.get()}",
            url: "${githubRepo.get()}/raw/${branchName.get()}/${libName.get()}.xcframework.zip",
            checksum: "<CHECKSUM_HERE>"
        ),
    ]
)
"""

        println("Writing initial package...")
       packageFile.get().writeText(packageManifest)

        ByteArrayOutputStream().let {
            project.exec {
                commandLine("swift", "package", "compute-checksum", "${archivePath.get()}")
                standardOutput = it
            }

            val checksum = it.toString().replace("\n", "")
            val finalManifest = packageManifest.replace("<CHECKSUM_HERE>", checksum)

            println("Writing final package...")
            packageFile.get().writeText(finalManifest)
        }
    }
}