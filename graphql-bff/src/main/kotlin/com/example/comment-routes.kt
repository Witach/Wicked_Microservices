package com.example

import com.example.servicechassis.KafkaObjectMapper
import com.example.servicechassis.kafkaProxy
import com.example.servicechassis.map
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate
import org.springframework.web.servlet.function.RouterFunction
import org.springframework.web.servlet.function.ServerResponse
import org.springframework.web.servlet.function.body
import org.springframework.web.servlet.function.router

fun commentRoutes(replyingKafkaTemplate: ReplyingKafkaTemplate<String, String, String>, kafkaObjectMapper: KafkaObjectMapper): RouterFunction<ServerResponse> {
    return router {
        path("/comment").nest {
            POST("") {
            }
            DELETE("{profileId}") {
            }
            POST("/{commentId}/reply") {
            }
            PUT("/{commentId}/reply/{replyId}") {
            }
            DELETE("/{commentId}/reply/{replyId}") {
            }
        }
    }
}