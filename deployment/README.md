### Установка и запуск minikube 

Просто следуете вот этому гайду и все https://kubernetes.io/ru/docs/tasks/tools/install-kubectl/ .

После установки выполняете команду:

    minikube start

Чтоб открыть доступ до сети кубера используйте:

    kubectl proxy

При этом запускается прокси, который работает пока вы не закроете его. Также он заблокирует терминал до завершения.
После запуска в консоли отобразиться адрес и порт, по которому можно обращаться в api.

Забилдить image и добавить образ в registry minikube'а можно грейдл таской

    ./gradlew buildImageAndPublishToMinikube

Применить deployments и services можно таской

    ./gradlew applyDeployments
