package com.example.service

import com.example.servicechassis.KafkaClient
import com.example.servicechassis.KafkaObjectMapper
import com.example.servicechassis.kafkaProxy
import com.example.servicechassis.kafkaProxyFeign
import org.apache.kafka.clients.consumer.ConsumerRecords
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.apache.kafka.clients.consumer.OffsetAndTimestamp
import org.apache.kafka.common.TopicPartition
import org.springframework.context.annotation.Profile
import org.springframework.kafka.core.DefaultKafkaConsumerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate
import org.springframework.stereotype.Component
import java.sql.Timestamp
import java.time.Instant
import java.util.*


@Component
@Profile("feign")
class GroupServiceClientImpl(val groupServiceFeignClient: GroupServiceFeignClient): GroupServiceClient {
    override fun existsById(group: UUID): Boolean {
        return groupServiceFeignClient.existsById(group).exists
    }
}

@Component
@Profile("kafka")
class GroupServiceClientImplKafka(val kafkaClient: ReplyingKafkaTemplate<String, String, String>, val kafkaObjectMapper: KafkaObjectMapper): GroupServiceClient {
    override fun existsById(group: UUID): Boolean {
        val response = kafkaProxyFeign {
            kafkaTemplate = kafkaClient
            requestTopic = "group-exists-request"
            responseTopic = "group-exists-response"
            post = kafkaObjectMapper.convertToMessageFromPathVariable("groupId" to group.toString())
        }()
        return response == "true"
    }
}

@Component
@Profile("dumb")
class GroupClientDumbImpl(): GroupServiceClient {
    override fun existsById(group: UUID): Boolean {
        return true
    }
}