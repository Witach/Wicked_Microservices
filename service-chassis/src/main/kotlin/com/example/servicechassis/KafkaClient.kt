package com.example.servicechassis

import com.fasterxml.jackson.databind.ObjectMapper
import org.apache.kafka.clients.consumer.ConsumerRecords
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.apache.kafka.clients.consumer.OffsetAndTimestamp
import org.apache.kafka.common.TopicPartition
import org.example.DomainEvent
import org.example.EventPublisher
import org.springframework.kafka.core.DefaultKafkaConsumerFactory
import org.springframework.kafka.core.KafkaTemplate
import java.time.Instant
import java.util.*

class KafkaClient(val kafka: KafkaTemplate<String, String>,
                  val objectMapper: ObjectMapper,
                  val consumerFactory: DefaultKafkaConsumerFactory<String, String>): EventPublisher {
    
    fun send(topic: String, key: String ,value: Map<String, Any>) {
        kafka.send(topic, key, objectMapper.writeValueAsString(value))
    }

    override fun publish(domainEvent: DomainEvent<*>, topic: String) {
//        send(topic, domainEvent.eventId.toString(), mapOf("event" to objectMapper.writeValueAsString(domainEvent)))
    }

    fun receiveSyncResp(topic: String, key: String): Map<String, String>? {
        val consumer = consumerFactory.createConsumer() as KafkaConsumer<String, String>

        val map = consumer.listTopics()
        val partitionInfo = map[topic]!![0]
        val topicPartition = TopicPartition(partitionInfo.topic(), partitionInfo.partition())

        consumer.assign(Arrays.asList(topicPartition))

        val timestampsToSearch: MutableMap<TopicPartition, Long> = HashMap()
        timestampsToSearch[topicPartition] = Instant.now().toEpochMilli()

        val map1: Map<TopicPartition, OffsetAndTimestamp> = consumer.offsetsForTimes(timestampsToSearch)

        consumer.seek(topicPartition, map1[topicPartition]!!.offset())


        val records: ConsumerRecords<String, String> = consumer.poll(1000)

        var rest: Map<String, String>? = null

        records.forEach {
            if (it.key() == key) {
                rest = objectMapper.readValue(it.value(), Map::class.java) as Map<String, String>
            }
        }
        consumer.close()
        return rest
    }
    
}

class EventPublisherMock: EventPublisher {
    val listMap = mutableListOf<Map<String, Any>>()
        get

    override fun publish(domainEvent: DomainEvent<*>, topic: String) {
        listMap.add(mapOf("event" to domainEvent, "topic" to topic))
    }


}