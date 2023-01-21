//package com.example
//
//import com.example.servicechassis.KafkaObjectMapper
//import com.example.servicechassis.kafkaProxy
//import com.example.servicechassis.map
//import org.springframework.kafka.requestreply.ReplyingKafkaTemplate
//import org.springframework.web.servlet.function.RouterFunction
//import org.springframework.web.servlet.function.ServerResponse
//import org.springframework.web.servlet.function.body
//import org.springframework.web.servlet.function.router
//
//fun groupRoutes(replayingKafkaTemplate: ReplyingKafkaTemplate<String, String, String>, kafkaObjectMapper: KafkaObjectMapper): RouterFunction<ServerResponse> {
//    return router {
//        path("/group").nest {
//
//            GET("/post") {
//            }
//            GET("") {
//            }
//            POST("") {
//            }
//            GET("/{groupId}") {
//            }
//            GET("/{groupId}/exists") {
//            }
//            DELETE("/{groupId}") {
//            }
//            DELETE("/{groupId}/profile/{profileId}") {
//            }
//            PUT("/{groupId}") {
//            }
//            POST("/{groupId}/profile") {
//            }
//        }
//    }
//}