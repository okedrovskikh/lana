package lana.deploy

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction
import java.io.InputStreamReader
import java.io.SequenceInputStream
import java.util.concurrent.Executors

abstract class PublishDockerImageToLocalMinikube : DefaultTask() {
    private val coroutinePool = Executors.newFixedThreadPool(5) // These threads + gradle daemon thread are actually 1

    @get:Input
    abstract var imageName: String

    @get:Input
    open var version: String = "latest"

    @TaskAction
    fun execute() {
        val proc = TerminalCommand("minikube image load $imageName:$version").runCommand()
        val out = InputStreamReader(SequenceInputStream(proc.inputStream, proc.errorStream))
        coroutinePool.submit { out.forEachLine { println(it) } }
        val termCode = proc.waitFor()
        coroutinePool.shutdown()
        if (termCode != 0) error("Not 0 termination code")
    }
}
