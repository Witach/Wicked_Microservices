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
                kafkaProxy {
                    requestTopic = "comment-create-request"
                    responseTopic = "comment-create-response"
                    kafkaTemplate = replyingKafkaTemplate
                    post = kafkaObjectMapper.convertToMessageFromBodyObject(it.body())
                } ()
            }
            DELETE("{profileId}") {
                kafkaProxy {
                    requestTopic = "comment-delete-request"
                    responseTopic = "comment-delete-response"
                    kafkaTemplate = replyingKafkaTemplate
                    post = kafkaObjectMapper.convertToMessageFromPathVariable ("profileId" to it.pathVariable("profileId"))
                } ()
            }
            POST("/{commentId}/reply") {
                kafkaProxy {
                    requestTopic = "reply-create-request"
                    responseTopic = "reply-create-response"
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
                    responseTopic = "reply-edit-response"
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
                    responseTopic = "reply-delete-response"
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