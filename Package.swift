
// swift-tools-version:5.3
import PackageDescription

let package = Package(
    name: "lostintravelsdk",
    platforms: [
        .iOS(.v13)
    ],
    products: [
        // Products define the executables and libraries a package produces, and make them visible to other packages.
        .library(
            name: "lostintravelsdk",
            targets: ["lostintravelsdk"])
    ],
    dependencies: [
        // Dependencies declare other packages that this package depends on.
    ],
    targets: [
        // Targets are the basic building blocks of a package. A target can define a module or a test suite.
        // Targets can depend on other targets in this package, and on products in packages this package depends on.
        .binaryTarget(
            name: "lostintravelsdk",
            url: "https://github.com/mobileappconsultant/lostintravelsharedlibrary/raw/artifacts/lostintravelsdk.xcframework.zip",
            checksum: "a832a671d46c7462da65cff7e11c4d8457963234444ae055df12478df3e4a1b0"
        ),
    ]
)
