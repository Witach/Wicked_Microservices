import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
    id("org.example.social-app-module")
}

group = "com.example"
version = "0.0.1-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":post-domain-module"))
    implementation(project(":service-chassis"))
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}

tasks.withType<BootJar> {
    manifest {
        attributes["Start-Class"] = "com.example.PostAppKt"
    }
}
