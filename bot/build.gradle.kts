plugins {
    id("org.springframework.boot") version "3.0.5"
    id("io.spring.dependency-management") version "1.1.0"
    id("java")
}

group = "urfu.ru"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

configurations {
    compileOnly {
        extendsFrom(configurations.findByName("annotationProcessor"))
    }
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("io.micrometer:micrometer-registry-prometheus")
    compileOnly("org.projectlombok:lombok")
    compileOnly("org.mapstruct:mapstruct:1.5.3.Final")
    compileOnly("jakarta.annotation:jakarta.annotation-api:1.3.5")
    annotationProcessor("org.projectlombok:lombok")
    annotationProcessor("org.mapstruct:mapstruct-processor:1.5.3.Final")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.testcontainers:junit-jupiter")
}

tasks {
    compileJava {
        options.encoding ="UTF-8"
    }

    compileTestJava {
        options.encoding = "UTF-8"
    }

    springBoot {
        buildInfo()
    }

    test {
        useJUnitPlatform()
    }

}