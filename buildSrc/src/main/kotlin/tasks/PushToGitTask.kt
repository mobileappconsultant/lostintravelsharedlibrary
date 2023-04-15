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

    @get:Input
    abstract val artifactsToTransfer: ListProperty<String>

    private fun runCatch(cb: () -> Unit) {
        try {
            cb()
        } catch (e: Exception) {
            println("Exception: $e")
        }
    }

    @TaskAction
    fun execute() {
        // Add uncommitted files and the artifacts we need

        runCatch {
            execCmdline(arrayOf("git", "add", "-A"))

            // Stash main branch
            execCmdline(arrayOf("git", "stash"))
        }

        // Force-add artifacts
        execCmdline(arrayOf("git", "add", *artifactsToTransfer.get().toTypedArray(), "-f"))

        // Stash the changes
        execCmdline(arrayOf("git", "stash"))

        // Checkout artifacts branch
        execCmdline(arrayOf("git", "checkout", branch.get()))

        // Pull
        runCatch {
            execCmdline(arrayOf("git", "pull"))
        }

        // Remove the files to prevent conflicts
        runCatch {
            artifactsToTransfer.get().forEach {
                execCmdline(arrayOf("git", "rm", it))
            }
        }

        // Pop the stash
        execCmdline(arrayOf("git", "stash", "pop"))

        // Add those files
        execCmdline(arrayOf("git", "add", *artifactsToTransfer.get().toTypedArray()))

        // Commit them
        execCmdline(arrayOf("git", "commit", "-m", commitMessage.get()))

        // Push
        execCmdline(arrayOf("git", "push", *extraPushArgs.get().toTypedArray()))

        // Checkout main branch
        execCmdline(arrayOf("git", "checkout", originalBranch.get()))

        // Restore the stuff
        runCatch {
            artifactsToTransfer.get().forEach {
                execCmdline(arrayOf("git", "stash", "pop"))
            }
        }
    }

    private fun execCmdline(args: Array<String>) {

        project.exec {
            commandLine(*args)
        }
    }

}