package com.example.servicechassis

import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.kafka.common.header.internals.RecordHeader
import org.springframework.messaging.Message
import org.springframework.messaging.support.MessageBuilder

class KafkaAnswerTemplate(val sessionStorage: ImperativeSessionStorage, val kafkaObjectMapper: KafkaObjectMapper) {
    fun answer(request: ConsumerRecord<String, String>, funx: () -> String): Message<String> {
        val sessionId = request.headers().headers("session-id").singleOrNull() ?: ""
        sessionStorage.userId = kafkaObjectMapper.readSession(request.value())
        val response = tryToResponse {
            funx()
        }
        return MessageBuilder.withPayload(response)
            .setHeader("session-id", (sessionId as RecordHeader).value())
            .build()
    }
}