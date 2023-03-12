package com.example

import com.example.servicechassis.KafkaObjectMapper
import com.example.servicechassis.kafkaProxy
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.web.servlet.function.RouterFunction
import org.springframework.web.servlet.function.ServerResponse
import org.springframework.web.servlet.function.body
import org.springframework.web.servlet.function.router

fun postRoutes(replyingKafakTemplate: KafkaTemplate<String, String>, kafkaObjectMapper: KafkaObjectMapper): RouterFunction<ServerResponse> {
    return router {
        path("/post").nest {
            POST("") {
                kafkaProxy {
                    requestTopic = "post-create-request"
                    kafkaTemplate = replyingKafakTemplate
                    post = kafkaObjectMapper.convertToMessageFromBodyObject(it.body())
                } ()
            }
            GET("") {
                kafkaProxy {
                    requestTopic = "post-get-request"
                    kafkaTemplate = replyingKafakTemplate
                    post = kafkaObjectMapper.convertToMessageFromBodyObject(it.body())
                } ()
            }
            DELETE("{profileId}") {
                kafkaProxy {
                    requestTopic = "post-delete-request"
                    kafkaTemplate = replyingKafakTemplate
                    post = kafkaObjectMapper.convertToMessageFromPathVariable("profileId" to it.pathVariable("profileId"))
                } ()
            }
            PUT("{postId}") {
                kafkaProxy {
                    requestTopic = "post-update-request"
                    kafkaTemplate = replyingKafakTemplate
                    post = kafkaObjectMapper.convertToMessageFrom {
                        pathVariable = mapOf(
                            "postId" to it.pathVariable("postId")
                        )
                        body = it.body()
                    }
                } ()
            }
            DELETE("/{postId}/attachment/{attachmentId}") {
                kafkaProxy {
                    requestTopic = "post-deleteattachment-request"
                    kafkaTemplate = replyingKafakTemplate
                    post = kafkaObjectMapper.convertToMessageFrom {
                        pathVariable = mapOf(
                            "postId" to it.pathVariable("postId"),
                            "attachmentId" to it.pathVariable("attachmentId")
                        )
                    }
                }()
            }
        }
    }
}