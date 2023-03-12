package com.example

import com.example.servicechassis.KafkaObjectMapper
import com.example.servicechassis.kafkaProxy
import com.example.servicechassis.map
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.web.servlet.function.RouterFunction
import org.springframework.web.servlet.function.ServerResponse
import org.springframework.web.servlet.function.body
import org.springframework.web.servlet.function.router

fun commentRoutes(replyingKafkaTemplate: KafkaTemplate<String, String>, kafkaObjectMapper: KafkaObjectMapper): RouterFunction<ServerResponse> {
    return router {
        path("/comment").nest {
            POST("") {
                kafkaProxy {
                    requestTopic = "comment-create-request"
                    kafkaTemplate = replyingKafkaTemplate
                    post = kafkaObjectMapper.convertToMessageFromBodyObject(it.body())
                } ()
            }
            DELETE("{profileId}") {
                kafkaProxy {
                    requestTopic = "comment-delete-request"
                    kafkaTemplate = replyingKafkaTemplate
                    post = kafkaObjectMapper.convertToMessageFromPathVariable ("profileId" to it.pathVariable("profileId"))
                } ()
            }
            POST("/{commentId}/reply") {
                kafkaProxy {
                    requestTopic = "reply-create-request"
                    kafkaTemplate = replyingKafkaTemplate
                    post = kafkaObjectMapper.convertToMessageFrom {
                        pathVariable = mapOf(
                            "commentId" to it.pathVariable("commentId")
                        )
                        body = it.body()
                    }
                } ()
            }
            PUT("/{commentId}/reply/{replyId}") {
                kafkaProxy {
                    requestTopic = "reply-edit-request"
                    kafkaTemplate = replyingKafkaTemplate
                    post = kafkaObjectMapper.convertToMessageFrom {
                        pathVariable = mapOf(
                            "commentId" to it.map("commentId").toString(),
                            "replyId" to it.map("replyId").toString()
                        )
                        body = it.body()
                    }
                } ()
            }
            DELETE("/{commentId}/reply/{replyId}") {
                kafkaProxy {
                    requestTopic = "reply-delete-request"
                    kafkaTemplate = replyingKafkaTemplate
                    post = kafkaObjectMapper.convertToMessageFrom {
                        pathVariable = mapOf(
                            "commentId" to it.map("commentId").toString(),
                            "replyId" to it.map("replyId").toString()
                        )
                    }
                } ()
            }
        }
    }
}