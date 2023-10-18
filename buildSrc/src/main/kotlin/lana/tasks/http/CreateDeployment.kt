package lana.tasks.http

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import kotlin.io.path.Path

abstract class CreateDeployment : DefaultTask() {
    private val httpClient: HttpClient = HttpClient.newBuilder()
        .version(HttpClient.Version.HTTP_1_1)
        .build()

    @get:Input
    abstract var deploymentName: String

    @get:Input
    abstract var deploymentYaml: String

    @get:Input
    abstract var serverAddress: String

    private val postDeploymentUrl get() = "$serverAddress/apis/apps/v1/namespaces/default/deployments/$deploymentName"

    @TaskAction
    fun execute() {
        val request = HttpRequest.newBuilder()
            .uri(URI.create(postDeploymentUrl))
            .PUT(HttpRequest.BodyPublishers.ofFile(Path(deploymentYaml)))
            .header("Content-Type", "application/yaml")
            .build()

        val response = httpClient.send(request, HttpResponse.BodyHandlers.ofString())

        if (response.statusCode() != 200) {
            println(response.statusCode())
            println(response.body())
            error(response.body())
        }
    }
}
