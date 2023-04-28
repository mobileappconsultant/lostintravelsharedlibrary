
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
            checksum: "549015465970652d06a764597ebf3cf57364446d42fb76eb1657c97d5b2c3219"
        ),
    ]
)