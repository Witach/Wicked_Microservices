package com.example.service

import com.example.servicechassis.AuthServerClientImpl
import org.springframework.boot.CommandLineRunner

class LogginginCommand(val authServerClientImpl: AuthServerClientImpl): CommandLineRunner {
    override fun run(vararg args: String?) {
        authServerClientImpl.logIntoRealm()
    }
}