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

    private fun checkoutBranch(branch: String) {
        println("Checking out $branch branch...")
        execCmdline(arrayOf("git", "checkout", branch))
        println("$branch branch checked out!")
    }

    private fun checkoutMainBranch() {
        checkoutBranch(originalBranch.get())
    }

    private fun checkoutArtifactsBranch() {
        checkoutBranch(branch.get())
    }

    private fun addAllAndStash() {
        println("Adding all files and stashing em")

        execCmdline(arrayOf("git", "add", "-A"))
        // Stash branch
        stash()

        println("Added and stashed all files")
    }

    private fun addArtifactsToTransfer() {
        println("Adding ${artifactsToTransfer.get()} to be stashed")
        // Force-add artifacts
        execCmdline(arrayOf("git", "add", *artifactsToTransfer.get().toTypedArray(), "-f"))

        println("Added ${artifactsToTransfer.get()} to staging area")
    }

    private fun popStash() {
        println("Popping stash...")
        execCmdline(arrayOf("git", "stash", "pop"))
        println("Popped stash")
    }

    private fun stash() {
        println("Stashing...")
        execCmdline(arrayOf("git", "stash"))
        println("Stashed!")
    }

    @TaskAction
    fun execute() {
        // Add uncommitted files and the artifacts we need
        runCatch {
            addAllAndStash()
        }

        addArtifactsToTransfer()
        stash()

        // Checkout artifacts branch
        checkoutArtifactsBranch()

        // Pull
        runCatch {
            println("Checking if artifacts have upstream changes...")
            execCmdline(arrayOf("git", "pull"))
            println("Done checking for upstream changes")
        }

        // Remove the files to prevent conflicts
        runCatch {
            println("Removing ${artifactsToTransfer.get()} from index...")
            artifactsToTransfer.get().forEach {
                execCmdline(arrayOf("git", "rm", it))
            }
            println("Removed ${artifactsToTransfer.get()} from index")
        }

        // Pop the stash
        println("Popping ${artifactsToTransfer.get()} into ${branch.get()}...")
        popStash()
        println("Popped ${artifactsToTransfer.get()} into ${branch.get()}")

        // Add those files
        println("Adding ${artifactsToTransfer.get()} to ${branch.get()}...")
        execCmdline(arrayOf("git", "add", *artifactsToTransfer.get().toTypedArray()))
        println("Added ${artifactsToTransfer.get()} to ${branch.get()}")

        // Commit them
        println("Committing the changes...")
        execCmdline(arrayOf("git", "commit", "-m", commitMessage.get()))
        println("Committed the changes")

        // Push
        println("Pushing the update to ${branch.get()}...")
        execCmdline(arrayOf("git", "push", *extraPushArgs.get().toTypedArray()))
        println("Pushed the update to ${branch.get()}")

        // Checkout main branch
        checkoutMainBranch()

        // Restore the stuff
        runCatch {
            popStash()
        }
    }

    private fun execCmdline(args: Array<String>) {

        project.exec {
            commandLine(*args)
        }
    }

}