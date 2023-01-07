package com.example.service

import com.example.servicechassis.KafkaClient
import org.apache.kafka.clients.consumer.ConsumerRecords
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.apache.kafka.clients.consumer.OffsetAndTimestamp
import org.apache.kafka.common.TopicPartition
import org.springframework.context.annotation.Profile
import org.springframework.kafka.core.DefaultKafkaConsumerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component
import java.sql.Timestamp
import java.time.Instant
import java.util.*


@Component
@Profile("feign")
class GroupServiceClientImpl(val groupServiceFeignClient: GroupServiceFeignClient): GroupServiceClient {
    override fun existsById(group: UUID): Boolean {
        return groupServiceFeignClient.existsById(group).isExistsing
    }
}

@Component
@Profile("kafka")
class GroupServiceClientImplKafka(val kafkaClient: KafkaClient): GroupServiceClient {
    override fun existsById(group: UUID): Boolean {
        val key = UUID.randomUUID().toString()
        kafkaClient.send("group-exists-response", key, mapOf("groupId" to group.toString()))
        return kafkaClient.receiveSyncResp("group-exists-response", key)!!["groupExists"]!!.toBoolean()
    }
}

@Component
@Profile("dumb")
class GroupClientDumbImpl(): GroupServiceClient {
    override fun existsById(group: UUID): Boolean {
        return true
    }
}