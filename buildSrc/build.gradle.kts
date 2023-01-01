

plugins {
    `kotlin-dsl`
    id("maven-publish")
}

repositories {
    mavenCentral()
}

group = "org.example"
version = "1.9"


tasks.publish {
    dependsOn("check")
}

dependencies {
    implementation("org.springframework.boot:spring-boot-gradle-plugin:2.7.3")
    implementation("io.spring.gradle:dependency-management-plugin:1.0.13.RELEASE")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.6.21")
    implementation("org.jetbrains.kotlin:kotlin-allopen:1.6.21")
}