
import com.google.protobuf.gradle.*

plugins {
    id("java-library")
    id("com.google.protobuf") version "0.8.18"
}

group = "com.example"
version = "0.0.1-SNAPSHOT"


dependencies {
    implementation("io.grpc:grpc-protobuf:1.42.1") {
        exclude(group = "com.google.protobuf", module = "protobuf-java")
        exclude(group = "com.google.protobuf", module = "protobuf-java-util")
    }
    implementation("io.grpc:grpc-stub:1.42.1")
    implementation("com.google.protobuf:protobuf-java:3.19.1")
    implementation("com.google.protobuf:protobuf-java-util:3.19.1")
    compileOnly("jakarta.annotation:jakarta.annotation-api:1.3.5")
}

protobuf {
    protoc {
        artifact = "com.google.protobuf:protoc:3.7.1"
    }
    plugins {
        id("grpc") {
            artifact = "io.grpc:protoc-gen-grpc-java:1.20.0"
        }
    }
    generateProtoTasks {
        ofSourceSet("main").forEach {
            it.plugins {
                id("grpc")
            }
        }
    }
}

