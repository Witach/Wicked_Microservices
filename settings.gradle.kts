rootProject.name = "SocialApp"

pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenLocal()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositories {
        mavenLocal()
        mavenCentral()
    }
}
include("DDD")
include("projection-lib")
include("post-domain-module")
include("profile-domain-module")
include("feed-domain-module")
include("untitled")
include("post-app")
include("feed-app")
include("profile-app")
include("registry-server")
include("config-server")
include("api-gatewat")
include("service-chassis")
include("kafka-gateway")
include("graphql-bff")
include("grpc-interface")
