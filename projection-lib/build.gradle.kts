

plugins {
    id("org.example.social-app-library")
}

group = "com.example"
version = "0.0.1-SNAPSHOT"

dependencies {
    implementation(project(":DDD"))
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}