package com.example

import com.example.servicechassis.KafkaObjectMapper
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate
import org.springframework.web.servlet.function.RouterFunction
import org.springframework.web.servlet.function.ServerResponse
import org.springframework.web.servlet.function.body
import org.springframework.web.servlet.function.router

fun groupPostRoutes(replyingKafkaTemplate: ReplyingKafkaTemplate<String, String, String>, kafkaObjectMapper: KafkaObjectMapper): RouterFunction<ServerResponse> {
    return router {
        path("/group/post").nest {
            GET("") {
                kafkaProxy {
                    requestTopic = "grouppost-get-request"
                    responseTopic = "grouppost-get-response"
                    kafkaTemplate = replyingKafkaTemplate
                    post = kafkaObjectMapper.convertToMessageFromBodyObject(it.body())
                } ()
            }
            POST("") {
                kafkaProxy {
                    requestTopic = "grouppost-create-request"
                    responseTopic = "grouppost-create-response"
                    kafkaTemplate = replyingKafkaTemplate
                    post = kafkaObjectMapper.convertToMessageFromBodyObject(it.body())
                } ()
            }
            GET("/{groppostId}") {
                kafkaProxy {
                    requestTopic = "grouppost-get-request"
                    responseTopic = "grouppost-get-response"
                    kafkaTemplate = replyingKafkaTemplate
                    post = kafkaObjectMapper.convertToMessageFrom {
                        body = it.body()
                        pathVariable = mapOf(
                            "grouppostId" to it.pathVariable("grouppostId").toString()
                        )
                    }
                } ()
            }
        }
    }
}