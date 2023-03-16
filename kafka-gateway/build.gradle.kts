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

    implementation(project(":service-chassis"))
    implementation(project(":DDD"))
    implementation("org.springframework.session:spring-session-jdbc")
    implementation("com.h2database:h2:1.4.197")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}

tasks.withType<BootJar> {
    manifest {
        attributes["Start-Class"] = "com.example.KafkaGatewayKt"
    }
}
