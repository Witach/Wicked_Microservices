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
    implementation(project(":post-domain-module"))
    implementation("net.devh:grpc-client-spring-boot-starter:2.12.0.RELEASE")
    implementation("net.devh:grpc-server-spring-boot-starter:2.12.0.RELEASE")
    implementation(files("/home/dawid/Documents/projects/SocialApp/grpc-interface/build/libs/grpc-interface-0.0.1-SNAPSHOT.jar"))
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}

tasks.withType<BootJar> {
    manifest {
        attributes["Start-Class"] = "com.example.PostAppKt"
    }
}
