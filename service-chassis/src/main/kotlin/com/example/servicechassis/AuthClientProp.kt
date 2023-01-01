package com.example.servicechassis

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@ConstructorBinding
@ConfigurationProperties("auth-server")
data class AuthClientProp(
    val clientId: String,
    val clientSecret: String,
    val grantType: String,
    val uri: String,
    val authCode: String
)


