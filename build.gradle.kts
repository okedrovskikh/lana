import lana.tasks.command.line.TerminalCommandTask

tasks {
    val buildTask = register<TerminalCommandTask>("buildImage") {
        command = "docker build -t lana:latest ./service"
    }
    val publishTask = register<TerminalCommandTask>("publishToLocalMinikube") {
        command = "minikube image load lana:latest"
    }
    register("buildImageAndPublishToMinikube") {
        dependsOn(buildTask, publishTask)
    }

    val applyPostgresDeploymentAndServiceTask = register<TerminalCommandTask>("applyPostgres") {
        command = "kubectl apply -f ./deployment"
    }
    val applyLanaDeploymentAndServiceTask = register<TerminalCommandTask>("applyLana") {
        command = "kubectl apply -f ./service/deployment"
    }
    register("applyDeployments") {
        dependsOn(applyPostgresDeploymentAndServiceTask, applyLanaDeploymentAndServiceTask)
    }
}
