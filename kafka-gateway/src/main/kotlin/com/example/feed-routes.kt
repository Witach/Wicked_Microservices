package com.example

import com.example.servicechassis.KafkaObjectMapper
import com.example.servicechassis.kafkaProxy
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.web.servlet.function.RouterFunction
import org.springframework.web.servlet.function.ServerResponse
import org.springframework.web.servlet.function.router

fun feedRoutes(replyingKafkaTemplate: KafkaTemplate<String, String>, kafkaObjectMapper: KafkaObjectMapper): RouterFunction<ServerResponse> {
    return router {
        path("/feed/").nest {
            path("group").nest {
                GET("/{groupId}") {
                    kafkaProxy {
                        requestTopic = "feed-loadgroup-request"
                        kafkaTemplate = replyingKafkaTemplate
                        sessionId = ((it.session().getAttribute("session-id") ?: "" )as String)
                        post =
                            kafkaObjectMapper.convertToMessageFromPathVariable("groupId" to it.pathVariable("groupId"))
                    }()
                }
                GET("/{groupId}/post") {
                    kafkaProxy {
                        requestTopic = "feed-loadgrouppost-request"
                        kafkaTemplate = replyingKafkaTemplate
                        sessionId = ((it.session().getAttribute("session-id") ?: "" )as String)
                        post =
                            kafkaObjectMapper.convertToMessageFromPathVariable("groupId" to it.pathVariable("groupId"))
                    }()
                }
            }
            path("/profile").nest {
                GET("/{profileId}") {
                    kafkaProxy {
                        requestTopic = "feed-loadprofilepost-request"
                        kafkaTemplate = replyingKafkaTemplate
                        sessionId = ((it.session().getAttribute("session-id") ?: "" )as String)
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
                        kafkaTemplate = replyingKafkaTemplate
                        sessionId = ((it.session().getAttribute("session-id") ?: "" )as String)
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
                    kafkaTemplate = replyingKafkaTemplate
                    sessionId = ((it.session().getAttribute("session-id") ?: "" )as String)
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