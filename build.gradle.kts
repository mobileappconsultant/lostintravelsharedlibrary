plugins {
    //trick: for the same plugin versions in all sub-modules
    id("com.android.library").version("7.4.0").apply(false)
    kotlin("multiplatform").version("1.8.0").apply(false)
    kotlin("plugin.serialization") version Kotlin.version
   // id (Plugins.projectLevelKotlinSerialization) version Kotlin.version apply false
}





buildscript {

    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
    dependencies {

        classpath(Build.sqlDelightGradlePlugin)
        classpath(Build.graphQlPlugin)
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}