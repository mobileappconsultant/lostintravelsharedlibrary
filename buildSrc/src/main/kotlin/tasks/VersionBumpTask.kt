package tasks

import VersionManager
import org.gradle.api.DefaultTask
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction
import java.io.File

enum class VersionBumpType {
    MAJOR,
    MINOR,
    CODE,
}

abstract class VersionBumpTask: DefaultTask() {
    @get:Input
    abstract val file: Property<File>

    @get:Input
    abstract val bumpType: Property<VersionBumpType>

    private lateinit var manager: VersionManager

    @TaskAction
    fun execute() {
        println("Bumping version...")

        manager = VersionManager(file.get())

        when (bumpType.get()) {
            VersionBumpType.MAJOR -> manager.bumpMajor()
            VersionBumpType.MINOR -> manager.bumpMinor()
            VersionBumpType.CODE -> manager.bumpCode()
        }

        manager.saveVersionString()

        project.version = manager.versionToString()

        println("Bumped version")
    }

    fun newVersion(): String {
        return manager.versionToString()
    }

}