package com.example

import com.example.servicechassis.KafkaObjectMapper
import com.example.servicechassis.kafkaProxy
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate
import org.springframework.web.servlet.function.RouterFunction
import org.springframework.web.servlet.function.ServerResponse
import org.springframework.web.servlet.function.body
import org.springframework.web.servlet.function.router

fun profileRoutes(replayingKafkaTemplate: ReplyingKafkaTemplate<String, String, String>, kafkaObjectMapper: KafkaObjectMapper): RouterFunction<ServerResponse> {
    return router {
        path("/profile").nest {
            PUT("{profileId}") {}
            POST("/{profileId}/group") {}
            DELETE("/{profileId}/group") {}
            POST("/{profileId}/follow") {}
            DELETE("/{profileId}/follow") {}

            GET("") {}
            GET("/{profileId}") {}
        }
        path("/user").nest {
            POST("") {}
            DELETE("/{userId}") {}
            PUT("/{userId}") {}
            GET("/{userId}") {}
            GET("") {}
        }
    }
}
