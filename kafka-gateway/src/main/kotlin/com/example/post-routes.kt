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
                kafkaProxy {
                    requestTopic = "post-create-request"
                    responseTopic = "post-create-response"
                    kafkaTemplate = replyingKafakTemplate
                    post = kafkaObjectMapper.convertToMessageFromBodyObject(it.body())
                } ()
            }
            GET("") {
                kafkaProxy {
                    requestTopic = "post-get-request"
                    responseTopic = "post-get-response"
                    kafkaTemplate = replyingKafakTemplate
                    post = kafkaObjectMapper.convertToMessageFromBodyObject(it.body())
                } ()
            }
            DELETE("{profileId}") {
                kafkaProxy {
                    requestTopic = "post-delete-request"
                    responseTopic = "post-delete-response"
                    kafkaTemplate = replyingKafakTemplate
                    post = kafkaObjectMapper.convertToMessageFromPathVariable("profileId" to it.pathVariable("profileId"))
                } ()
            }
            PUT("{postId}") {
                kafkaProxy {
                    requestTopic = "post-update-request"
                    responseTopic = "post-update-response"
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
                    responseTopic = "post-deleteattachment-response"
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
// kafka-console-consumer.sh --topic post-get-request --from-beginning --bootstrap-server localhost:9092