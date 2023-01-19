package com.example

import com.example.servicechassis.KafkaObjectMapper
import com.example.servicechassis.kafkaProxy
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate
import org.springframework.web.servlet.function.RouterFunction
import org.springframework.web.servlet.function.ServerResponse
import org.springframework.web.servlet.function.router

fun feedRoutes(replyingKafkaTemplate: ReplyingKafkaTemplate<String, String, String>, kafkaObjectMapper: KafkaObjectMapper): RouterFunction<ServerResponse> {
    return router {
        path("/feed/").nest {
            path("group").nest {
                GET("/{groupId}") {
                }
                GET("/{groupId}/post") {
                }
            }
            path("/profile").nest {
                GET("/{profileId}") {
                }
                GET("/{profileId}/all") {
                }
            }
            GET("/{profileId}/all") {
            }
        }
    }
}