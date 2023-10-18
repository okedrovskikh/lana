package lana.tasks.command.line

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction
import java.io.InputStreamReader
import java.io.SequenceInputStream
import java.util.concurrent.Executors

abstract class TerminalCommandTask : DefaultTask() {
    private val executor = Executors.newFixedThreadPool(1) // These threads run on single gradle daemon thread

    @get:Input
    abstract var command: String

    @TaskAction
    fun execute() {
        val proc = TerminalCommand(command).runCommand()
        executor.execute {
            InputStreamReader(SequenceInputStream(proc.inputStream, proc.errorStream)).use {
                it.forEachLine { println(it) }
            }
        }
        val termCode = proc.waitFor()
        executor.shutdown()
        if (termCode != 0) error("Not 0 termination code")
    }
}

@JvmInline
value class TerminalCommand(private val command: String) {

    fun runCommand(): Process = ProcessBuilder(*command.split(" ").toTypedArray())
        .redirectInput(ProcessBuilder.Redirect.PIPE)
        .redirectOutput(ProcessBuilder.Redirect.PIPE)
        .redirectErrorStream(true)
        .start()
}
