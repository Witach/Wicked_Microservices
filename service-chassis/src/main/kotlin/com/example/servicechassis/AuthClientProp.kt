package com.example.servicechassis

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@ConstructorBinding
@ConfigurationProperties("auth-server")
data class AuthClientProp(
    var clientId: String,
    var clientSecret: String,
    var grantType: String,
    var uri: String,
    var authCode: String,
    var host: String
)


