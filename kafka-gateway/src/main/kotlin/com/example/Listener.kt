package com.example

import org.apache.kafka.clients.consumer.ConsumerRecord
import org.springframework.kafka.annotation.KafkaListener
import java.util.*


class Listener(val connectionStorage: SSEConnectionStorage) {

    @KafkaListener(topicPattern = ".*-response")
    fun onEvent(event: ConsumerRecord<String, String>) {
        event.headers().headers("session-id").singleOrNull()?.let {
            connectionStorage.sendData(UUID.nameUUIDFromBytes(it.value()).toString(), event.value())
        }
    }
}