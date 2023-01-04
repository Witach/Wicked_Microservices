plugins {
    id("org.example.social-app-library")
}

group = "com.example"
version = "0.0.1-SNAPSHOT"

dependencies {
    implementation(project(":DDD"))
    implementation("org.springframework.boot:spring-boot-starter-web:2.7.7")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}