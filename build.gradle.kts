plugins {
    id("org.example.social-app-module")
}

group = "com.example"
version = "0.0.1-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {

}

tasks.withType<Test> {
    useJUnitPlatform()
}
