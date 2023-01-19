package com.example

import com.example.servicechassis.KafkaObjectMapper
import com.example.servicechassis.kafkaProxy
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate
import org.springframework.web.servlet.function.RouterFunction
import org.springframework.web.servlet.function.ServerResponse
import org.springframework.web.servlet.function.body
import org.springframework.web.servlet.function.router

fun postRoutes(replyingKafakTemplate: ReplyingKafkaTemplate<String, String, String>, kafkaObjectMapper: KafkaObjectMapper): RouterFunction<ServerResponse> {
    return router {
        path("/post").nest {
            POST("") {
            }
            GET("") {
            }
            DELETE("{profileId}") {
            }
            PUT("{postId}") {
            }
            DELETE("/{postId}/attachment/{attachmentId}") {
            }
        }
    }
}
// kafka-console-consumer.sh --topic post-get-request --from-beginning --bootstrap-server localhost:9092