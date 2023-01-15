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
                    kafkaProxy {
                        requestTopic = "feed-loadgroup-request"
                        responseTopic = "feed-loadgroup-response"
                        kafkaTemplate = replyingKafkaTemplate
                        post =
                            kafkaObjectMapper.convertToMessageFromPathVariable("groupId" to it.pathVariable("groupId"))
                    }()
                }
                GET("/{groupId}/post") {
                    kafkaProxy {
                        requestTopic = "feed-loadgrouppost-request"
                        responseTopic = "feed-loadgrouppost-response"
                        kafkaTemplate = replyingKafkaTemplate
                        post =
                            kafkaObjectMapper.convertToMessageFromPathVariable("groupId" to it.pathVariable("groupId"))
                    }()
                }
            }
            path("/profile").nest {
                GET("/{profileId}") {
                    kafkaProxy {
                        requestTopic = "feed-loadprofilepost-request"
                        responseTopic = "feed-loadprofilepost-response"
                        kafkaTemplate = replyingKafkaTemplate
                        post = kafkaObjectMapper.convertToMessageFrom {
                            pathVariable = mapOf("profileId" to it.pathVariable("profileId"))
                            param = mapOf(
                                "page" to it.param("page").orElse("0"),
                                "size" to it.param("size").orElse("20")
                            )
                        }
                    }()
                }
                GET("/{profileId}/all") {
                    kafkaProxy {
                        requestTopic = "feed-search-request"
                        responseTopic = "feed-search-response"
                        kafkaTemplate = replyingKafkaTemplate
                        post = kafkaObjectMapper.convertToMessageFrom {
                            pathVariable = mapOf("profileId" to it.pathVariable("profileId"))
                            param = mapOf(
                                "page" to it.param("page").orElse("0"),
                                "size" to it.param("size").orElse("20")
                            )
                        }
                    }()
                }
            }
            GET("/{profileId}/all") {
                kafkaProxy {
                    requestTopic = "feed-search-request"
                    responseTopic = "feed-search-response"
                    kafkaTemplate = replyingKafkaTemplate
                    post = kafkaObjectMapper.convertToMessageFrom {
                        pathVariable = mapOf("profileId" to it.pathVariable("profileId"))
                        param = mapOf(
                            "page" to it.param("page").orElse("0"),
                            "size" to it.param("size").orElse("20")
                        )
                    }
                }()
            }
        }
    }
}