import lana.deploy.BuildImageTask
import lana.deploy.PublishDockerImageToLocalMinikube

tasks {
    val buildTask = register<BuildImageTask>("buildImage") {
        imageName = "lana"
        version = "1.0.0"
        dockerfilePath = "./service"
    }
    val publishTask = register<PublishDockerImageToLocalMinikube>("publishToMinikube") {
        imageName = "lana"
        version = "1.0.0"
    }
    register("buildImageAndPublishToMinikube") {
        dependsOn(buildTask, publishTask)
    }
}
