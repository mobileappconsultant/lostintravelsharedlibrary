package tasks

import org.gradle.api.DefaultTask
import org.gradle.api.model.ObjectFactory
import org.gradle.api.provider.ListProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction
import org.gradle.kotlin.dsl.listProperty

abstract class PushToGitTask: DefaultTask() {

    @get:Input
    abstract val originalBranch: Property<String>

    @get:Input
    abstract val branch: Property<String>

    @get:Input
    abstract val commitMessage: Property<String>

    @get:Input
    abstract val filesToCommit: ListProperty<String>

    @get:Input
    abstract val extraPushArgs: ListProperty<String>

    init {
        branch.convention("main")
        filesToCommit.convention(project.objects.listProperty())
        extraPushArgs.convention(project.objects.listProperty())
    }

    @TaskAction
    fun execute() {
        execCmdline(arrayOf("echo", "Printing CWD"))
        execCmdline(arrayOf("pwd"))
        execCmdline(arrayOf("git", "checkout", branch.get()))
        execCmdline(arrayOf("git", "add", filesToCommit.get().joinToString(" ")))
        execCmdline(arrayOf("git", "commit", "-m", commitMessage.get()))
        execCmdline(arrayOf("git", "push", *extraPushArgs.get().toTypedArray()))
        execCmdline(arrayOf("git", "checkout", originalBranch.get(), "-f"))
    }

    private fun execCmdline(args: Array<String>) {

        project.exec {
            commandLine(*args)
        }
    }

}