import gradle.kotlin.dsl.accessors._6e10767ad60ea274d08839ba451d91f7.implementation

plugins {
    kotlin("jvm")
    `java-library`
}

group = "com.example"
version = "0.0.1-SNAPSHOT"

dependencies {
    implementation(platform("org.jetbrains.kotlin:kotlin-bom"))
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("com.google.guava:guava:31.0.1-jre")
    testImplementation("org.jetbrains.kotlin:kotlin-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5") {
        exclude(group = "org.jetbrains.kotlin", module = "kotlin-test-junit")
    }
    testImplementation("org.mockito:mockito-core:3.+")
    testImplementation("org.mockito.kotlin:mockito-kotlin:4.0.0")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}