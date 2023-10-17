package lana.deploy

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction
import java.io.InputStreamReader
import java.io.SequenceInputStream
import java.util.concurrent.Executors

abstract class BuildImageTask : DefaultTask() {
    private val coroutinePool = Executors.newFixedThreadPool(5) // These threads + gradle daemon thread are actually 1

    @get:Input
    open var version: String = "latest"

    @get:Input
    abstract var imageName: String

    @get:Input
    open var dockerfilePath: String? = null

    @TaskAction
    fun execute() {
        val baseDockerCommand = "docker build -t $imageName:$version"
        val command = TerminalCommand(if (dockerfilePath == null) { baseDockerCommand } else { "$baseDockerCommand $dockerfilePath" })
        val proc = command.runCommand()
        val out = InputStreamReader(SequenceInputStream(proc.inputStream, proc.errorStream))
        coroutinePool.submit { out.forEachLine { println(it) } }
        val termCode = proc.waitFor()
        coroutinePool.shutdown()
        if (termCode != 0) error("Not 0 termination code")
    }
}
