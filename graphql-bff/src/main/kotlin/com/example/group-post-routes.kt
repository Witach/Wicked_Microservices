//package com.example
//
//import com.example.servicechassis.KafkaObjectMapper
//import com.example.servicechassis.kafkaProxy
//import org.springframework.kafka.requestreply.ReplyingKafkaTemplate
//import org.springframework.web.servlet.function.RouterFunction
//import org.springframework.web.servlet.function.ServerResponse
//import org.springframework.web.servlet.function.body
//import org.springframework.web.servlet.function.router
//
//fun groupPostRoutes(replyingKafkaTemplate: ReplyingKafkaTemplate<String, String, String>, kafkaObjectMapper: KafkaObjectMapper): RouterFunction<ServerResponse> {
//    return router {
//        path("/group/post").nest {
//            POST("") {
//            }
//            GET("/{groppostId}") {
//            }
//        }
//    }
//}

