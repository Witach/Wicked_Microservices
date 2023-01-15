package com.example

import com.example.servicechassis.KafkaObjectMapper
import com.example.servicechassis.kafkaProxy
import com.example.servicechassis.map
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate
import org.springframework.web.servlet.function.RouterFunction
import org.springframework.web.servlet.function.ServerResponse
import org.springframework.web.servlet.function.body
import org.springframework.web.servlet.function.router

fun groupRoutes(replayingKafkaTemplate: ReplyingKafkaTemplate<String, String, String>, kafkaObjectMapper: KafkaObjectMapper): RouterFunction<ServerResponse> {
    return router {
        path("/group").nest {

            GET("/post") {
                kafkaProxy {
                    requestTopic = "grouppost-getall-request"
                    responseTopic = "grouppost-getall-response"
                    kafkaTemplate = replayingKafkaTemplate
                    post = kafkaObjectMapper.convertToMessageFrom {
                        param = mapOf(
                            "groupIds" to it.param("groupIds").orElse("")
                        )
                    }
                } ()
            }
            GET("") {
                kafkaProxy {
                    requestTopic = "group-get-request"
                    responseTopic = "group-get-response"
                    kafkaTemplate = replayingKafkaTemplate
                    post = kafkaObjectMapper.convertToMessageFromBodyObject(it.body())
                } ()
            }
            POST("") {
                kafkaProxy {
                    requestTopic = "group-create-request"
                    responseTopic = "group-create-response"
                    kafkaTemplate = replayingKafkaTemplate
                    post = kafkaObjectMapper.convertToMessageFromBodyObject(it.body())
                } ()
            }
            GET("/{groupId}") {
                kafkaProxy {
                    requestTopic = "group-get-request"
                    responseTopic = "group-get-response"
                    kafkaTemplate = replayingKafkaTemplate
                    post = kafkaObjectMapper.convertToMessageFromPathVariable("groupId" to it.pathVariable("groupId").toString())
                } ()
            }
            GET("/{groupId}/exists") {
                kafkaProxy {
                    requestTopic = "group-exists-request"
                    responseTopic = "group-exists-response"
                    kafkaTemplate = replayingKafkaTemplate
                    post = kafkaObjectMapper.convertToMessageFromPathVariable("groupId" to it.pathVariable("groupId").toString())
                } ()
            }
            DELETE("/{groupId}") {
                kafkaProxy {
                    requestTopic = "group-delete-request"
                    responseTopic = "group-delete-response"
                    kafkaTemplate = replayingKafkaTemplate
                    post = kafkaObjectMapper.convertToMessageFromPathVariable("groupId" to it.pathVariable("groupId").toString())
                } ()
            }
            DELETE("/{groupId}/profile/{profileId}") {
                kafkaProxy {
                    requestTopic = "group-profileremove-request"
                    responseTopic = "group-profileremove-response"
                    kafkaTemplate = replayingKafkaTemplate
                    post = kafkaObjectMapper.convertToMessageFrom {
                        pathVariable = mapOf(
                            "groupId" to it.map("groupId").toString(),
                            "profileId" to it.map("profileId").toString()
                        )
                    }
                } ()
            }
            PUT("/{groupId}") {
                kafkaProxy {
                    requestTopic = "group-update-request"
                    responseTopic = "group-update-response"
                    kafkaTemplate = replayingKafkaTemplate
                    post = kafkaObjectMapper.convertToMessageFromPathVariable("groupId" to it.map("groupId").toString())
                } ()
            }
            POST("/{groupId}/profile") {
                kafkaProxy {
                    requestTopic = "group-profileadd-request"
                    responseTopic = "group-profileadd-response"
                    kafkaTemplate = replayingKafkaTemplate
                    post = kafkaObjectMapper.convertToMessageFrom {
                        pathVariable = mapOf(
                            "groupId" to it.map("groupId").toString(),
                        )
                        body = it.body()
                    }
                } ()
            }
        }
    }
}