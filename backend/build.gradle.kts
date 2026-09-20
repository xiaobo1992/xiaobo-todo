plugins {
    id("io.micronaut.application") version "4.4.4"
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

version = "0.1"
group = "com.xiaobo.todo"

repositories {
    mavenCentral()
}

dependencies {
    annotationProcessor("io.micronaut.serde:micronaut-serde-processor")
    annotationProcessor("io.micronaut.validation:micronaut-validation-processor")
    implementation("io.micronaut:micronaut-http-server-netty")
    implementation("io.micronaut.validation:micronaut-validation")
    implementation("io.micronaut.serde:micronaut-serde-jackson")
    implementation("io.micronaut.mongodb:micronaut-mongo-sync")
    runtimeOnly("ch.qos.logback:logback-classic")
    runtimeOnly("org.yaml:snakeyaml")
}

application {
    mainClass.set("com.xiaobo.todo.Application")
}

java {
    sourceCompatibility = JavaVersion.toVersion("21")
    targetCompatibility = JavaVersion.toVersion("21")
}

micronaut {
    version("4.6.3")
    runtime("netty")
    processing {
        incremental(true)
        annotations("com.xiaobo.todo.*")
    }
}
