
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
            checksum: "9bfe95e625a655cec7812e7fce703775628f4b8f247e7e51d941d9976f2835d2"
        ),
    ]
)
