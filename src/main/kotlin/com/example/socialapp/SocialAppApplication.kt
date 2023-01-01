package com.example.socialapp

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SocialAppApplication

fun main(args: Array<String>) {
    runApplication<SocialAppApplication>(*args)
}
