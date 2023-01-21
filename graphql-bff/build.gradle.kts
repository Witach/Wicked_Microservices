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
    implementation("com.graphql-java:graphql-java-extended-scalars:20.0")
    implementation("org.springframework.boot:spring-boot-starter-graphql")
    implementation(project(":projection-lib"))
    testImplementation("org.springframework.graphql:spring-graphql-test")
    implementation(project(":service-chassis"))
    implementation(project(":DDD"))
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}

tasks.withType<BootJar> {
    manifest {
        attributes["Start-Class"] = "com.example.GraphQLBFFKt"
    }
}
