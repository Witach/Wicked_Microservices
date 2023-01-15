package com.example.servicechassis

import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.common.header.internals.RecordHeader
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate
import org.springframework.kafka.requestreply.RequestReplyFuture
import org.springframework.kafka.support.KafkaHeaders
import org.springframework.kafka.support.SendResult
import org.springframework.web.servlet.function.ServerResponse


data class KafkaProxy(var requestTopic: String, var responseTopic: String,
                      var kafkaTemplate: ReplyingKafkaTemplate<String, String, String>?,
                      var post: String)


fun kafkaProxy(init: KafkaProxy.() -> Unit): () -> ServerResponse {
    val proxy = KafkaProxy("", "", null, "")
    proxy.init()
    return  {
        kafkaProxyFun(proxy.requestTopic, proxy.responseTopic,
            proxy.kafkaTemplate!!, proxy.post)
    }
}

fun kafkaProxyFun(requestTopic: String, responseTopic: String,
               kafkaTemplate: ReplyingKafkaTemplate<String, String, String>, post: String): ServerResponse {
    val consumerRecord = kafkaProxyFunFeign(requestTopic, responseTopic, kafkaTemplate, post)
    return ServerResponse.ok().body(consumerRecord)
}

fun kafkaProxyFunFeign(requestTopic: String, responseTopic: String,
                  kafkaTemplate: ReplyingKafkaTemplate<String, String, String>, post: String): String {

    val record: ProducerRecord<String, String> = ProducerRecord<String, String>(requestTopic, post)
    record.headers().add(RecordHeader(KafkaHeaders.REPLY_TOPIC, responseTopic.toByteArray()))

    val sendAndReceive: RequestReplyFuture<String, String, String> = kafkaTemplate.sendAndReceive(record)
    val sendResult: SendResult<String, String> = sendAndReceive.sendFuture.get()

    sendResult.producerRecord.headers().forEach { header ->
        println(
            header.key() + ":" + header.value().toString()
        )
    }
    val consumerRecord: ConsumerRecord<String, String> = sendAndReceive.get()
    return consumerRecord.value()
}

fun kafkaProxyFeign(init: KafkaProxy.() -> Unit): () -> String {
    val proxy = KafkaProxy("", "", null, "")
    proxy.init()
    return  {
        kafkaProxyFunFeign(proxy.requestTopic, proxy.responseTopic,
            proxy.kafkaTemplate!!, proxy.post)
    }
}