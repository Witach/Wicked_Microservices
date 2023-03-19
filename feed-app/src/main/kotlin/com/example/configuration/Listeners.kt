package com.example.configuration

import com.example.service.GroupServiceClientKafka
import com.example.service.PostServiceClientKafka
import com.example.servicechassis.KafkaAnswerTemplate
import com.example.servicechassis.KafkaObjectMapper
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.messaging.Message
import org.springframework.messaging.handler.annotation.SendTo

class Listeners(val kafklaObjectMapper: KafkaObjectMapper,
                val postService: PostServiceClientKafka,
                val groupService: GroupServiceClientKafka,
                val kafkaAnswerTemplate: KafkaAnswerTemplate) {

    @KafkaListener(topics = ["feed-loadgroup-request"])
    @SendTo("feed-loadgroup-response")
    fun `create-post-message`(content: ConsumerRecord<String, String>): Message<String> {
        return kafkaAnswerTemplate.answer(content) {
            val group = kafklaObjectMapper.readPathVariable(content, "groupId")
            kafklaObjectMapper.convertToMessageFromBodyObject(groupService.loadGroup(group))
        }
    }


    @KafkaListener(topics = ["feed-loadgrouppost-request"])
    @SendTo("feed-loadgrouppost-response")
    fun `feed-loadgroup-request`(content: ConsumerRecord<String, String>): Message<String>  {
        return kafkaAnswerTemplate.answer(content) {
            val group = kafklaObjectMapper.readPathVariable(content, "groupId")
            kafklaObjectMapper.convertToMessageFromBodyObject(groupService.loadGroupPosts(group))
        }

    }

    @KafkaListener(topics = ["feed-loadprofilepost-request"])
    @SendTo("feed-loadprofilepost-response")
    fun `feed-loadprofilepost-request`(content: ConsumerRecord<String, String>): Message<String>  {
        return kafkaAnswerTemplate.answer(content) {
            val group = kafklaObjectMapper.readPathVariable(content, "profileId")
            val page = kafklaObjectMapper.readParameter(content, "page", Int::class.java) ?: 0
            val size = kafklaObjectMapper.readParameter(content, "size", Int::class.java) ?: 0
            kafklaObjectMapper.convertToMessageFromBodyObject(postService.loadPosts(group, page, size))
        }
    }

    @KafkaListener(topics = ["feed-search-request"])
    @SendTo("feed-search-response")
    fun `feed-search-request`(content: ConsumerRecord<String, String>): Message<String>  {
        return kafkaAnswerTemplate.answer(content) {
            val feedSearch = kafklaObjectMapper.readPathVariable(content, "profileId")
            val page = kafklaObjectMapper.readParameter(content, "page", Int::class.java) ?: 0
            val size = kafklaObjectMapper.readParameter(content, "size", Int::class.java) ?: 0
            kafklaObjectMapper.convertToMessageFromBodyObject(postService.loadPostsWitGroupPosts(feedSearch, page, size))
        }
    }

}