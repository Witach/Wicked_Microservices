plugins {
    id("org.example.social-app-module")
}

group = "com.example"
version = "0.0.1-SNAPSHOT"

dependencies {
    api(project(":DDD"))
    api(project(":projection-lib"))
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}