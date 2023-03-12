package com.example.servicechassis

import org.apache.kafka.clients.producer.ProducerRecord
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.web.servlet.function.ServerResponse


data class KafkaProxy(var requestTopic: String,
                      var kafkaTemplate: KafkaTemplate<String, String>?,
                      var post: String)


fun kafkaProxy(init: KafkaProxy.() -> Unit): () -> ServerResponse {
    val proxy = KafkaProxy("", null, "")
    proxy.init()
    return  {
        kafkaProxyFun(proxy.requestTopic,
            proxy.kafkaTemplate!!, proxy.post)
        ServerResponse.ok().build()
    }
}

fun kafkaProxyFun(requestTopic: String,
               kafkaTemplate: KafkaTemplate<String, String>, post: String): ServerResponse {
    val consumerRecord = kafkaProxyFunFeign(requestTopic, kafkaTemplate, post)
    return ServerResponse.ok().body(consumerRecord)
}

fun kafkaProxyFunFeign(requestTopic: String,
                  kafkaTemplate: KafkaTemplate<String, String>, post: String): String {

    val record: ProducerRecord<String, String> = ProducerRecord<String, String>(requestTopic, post)
    kafkaTemplate.send(record)
    return "OK"
}

fun kafkaProxyFeign(init: KafkaProxy.() -> Unit): () -> String {
    val proxy = KafkaProxy("",  null, "")
    proxy.init()
    return  {
        kafkaProxyFunFeign(proxy.requestTopic,
            proxy.kafkaTemplate!!, proxy.post)
    }
}