package com.example

import com.example.servicechassis.KafkaObjectMapper
import com.example.servicechassis.kafkaProxy
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.web.servlet.function.RouterFunction
import org.springframework.web.servlet.function.ServerResponse
import org.springframework.web.servlet.function.body
import org.springframework.web.servlet.function.router

fun profileRoutes(replayingKafkaTemplate: KafkaTemplate<String, String>, kafkaObjectMapper: KafkaObjectMapper): RouterFunction<ServerResponse> {
    return router {
        path("/profile").nest {
            PUT("{profileId}") {
                kafkaProxy {
                    requestTopic = "profile-update-request"
                    kafkaTemplate = replayingKafkaTemplate
                    sessionId = ((it.session().getAttribute("session-id") ?: "" )as String)
                    post = kafkaObjectMapper.convertToMessageFrom {
                        pathVariable = mapOf(
                            "profileId" to it.pathVariable("profileId")
                        )
                        body = it.body()
                    }
                } ()
            }
            POST("/{profileId}/group") {
                kafkaProxy {
                    requestTopic = "profile-addtogroup-request"
                    kafkaTemplate = replayingKafkaTemplate
                    sessionId = ((it.session().getAttribute("session-id") ?: "" )as String)
                    post = kafkaObjectMapper.convertToMessageFrom {
                        pathVariable = mapOf(
                            "profileId" to it.pathVariable("profileId")
                        )
                        param = mapOf(
                            "groupId" to it.param("groupId").orElse("")
                        )
                    }
                } ()
            }
            DELETE("/{profileId}/group") {
                kafkaProxy {
                    requestTopic = "profile-removefromgroup-request"
                    kafkaTemplate = replayingKafkaTemplate
                    sessionId = ((it.session().getAttribute("session-id") ?: "" )as String)
                    post = kafkaObjectMapper.convertToMessageFrom {
                        pathVariable = mapOf(
                            "profileId" to it.pathVariable("profileId")
                        )
                        param = mapOf(
                            "groupId" to it.param("groupId").orElse("")
                        )
                    }
                } ()
            }
            POST("/{profileId}/follow") {
                kafkaProxy {
                    requestTopic = "profile-starttofollow-request"
                    kafkaTemplate = replayingKafkaTemplate
                    sessionId = ((it.session().getAttribute("session-id") ?: "" )as String)
                    post = kafkaObjectMapper.convertToMessageFrom {
                        pathVariable = mapOf(
                            "profileId" to it.pathVariable("profileId")
                        )
                        body = it.body()
                    }
                } ()
            }
            DELETE("/{profileId}/follow") {
                kafkaProxy {
                    requestTopic = "profile-stoptofollow-request"
                    kafkaTemplate = replayingKafkaTemplate
                    sessionId = ((it.session().getAttribute("session-id") ?: "" )as String)
                    post = kafkaObjectMapper.convertToMessageFrom {
                        pathVariable = mapOf(
                            "profileId" to it.pathVariable("profileId")
                        )
                        param = mapOf(
                            "profileToFollow" to it.param("profileToFollow").orElse("")
                        )
                    }
                } ()
            }

            GET("") {
                kafkaProxy {
                    requestTopic = "profile-getall-request"
                    kafkaTemplate = replayingKafkaTemplate
                    sessionId = ((it.session().getAttribute("session-id") ?: "" )as String)
                    post = kafkaObjectMapper.convertToMessageFrom {
                        param = mapOf(
                            "groupId" to it.param("groupId").orElse("")
                        )
                    }
                } ()
            }
            GET("/{profileId}") {
                kafkaProxy {
                    requestTopic = "profile-get-request"
                    kafkaTemplate = replayingKafkaTemplate
                    sessionId = ((it.session().getAttribute("session-id") ?: "" )as String)
                    post = kafkaObjectMapper.convertToMessageFromPathVariable("profileId" to it.pathVariable("profileId"))
                } ()
            }
        }
        path("/user").nest {
            POST("") {
                kafkaProxy {
                    requestTopic = "user-creat-request"
                    kafkaTemplate = replayingKafkaTemplate
                    sessionId = ""
                    post = kafkaObjectMapper.convertToMessageFromBodyObject(it.body())
                } ()
            }
            DELETE("/{userId}") {
                kafkaProxy {
                    requestTopic = "user-delete-request"
                    kafkaTemplate = replayingKafkaTemplate
                    sessionId = ((it.session().getAttribute("session-id") ?: "" )as String)
                    post = kafkaObjectMapper.convertToMessageFromPathVariable("userId" to it.pathVariable("userId"))
                } ()
            }
            PUT("/{userId}") {
                kafkaProxy {
                    requestTopic = "user-update-request"
                    kafkaTemplate = replayingKafkaTemplate
                    sessionId = ((it.session().getAttribute("session-id") ?: "" )as String)
                    post = kafkaObjectMapper.convertToMessageFrom {
                        pathVariable = mapOf(
                            "userId" to it.pathVariable("userId")
                        )
                        body = it.body()
                    }
                } ()
            }
            GET("/{userId}") {
                kafkaProxy {
                    requestTopic = "user-get-request"
                    kafkaTemplate = replayingKafkaTemplate
                    sessionId = ((it.session().getAttribute("session-id") ?: "" )as String)
                    post = kafkaObjectMapper.convertToMessageFromPathVariable("userId" to it.pathVariable("userId"))
                } ()
            }
            GET("") {
                kafkaProxy {
                    requestTopic = "user-getall-request"
                    kafkaTemplate = replayingKafkaTemplate
                    sessionId = ((it.session().getAttribute("session-id") ?: "" )as String)
                    post = kafkaObjectMapper.convertToMessageFromPathVariable("x" to "x")
                } ()
            }
        }
    }
}
