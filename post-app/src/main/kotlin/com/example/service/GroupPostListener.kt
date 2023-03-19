package com.example.service

import com.example.FeedSearch
import com.example.GroupPostCreateProjection
import com.example.UpdatePostProjection
import com.example.applicationservice.GroupPostService
import com.example.applicationservice.PostService
import com.example.servicechassis.*
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.messaging.Message
import org.springframework.messaging.handler.annotation.SendTo
import java.util.*

class GroupPostListener(
    val groupPostService: GroupPostService,
    val postService: PostService,
    val kafkaObjectMapper: KafkaObjectMapper,
    val kafkaAnswerTemplate: KafkaAnswerTemplate
) {
    @KafkaListener(topics = ["grouppost-create-request"])
    @SendTo("grouppost-create-response")
    fun `create-grouppost-message`(content: ConsumerRecord<String, String>): Message<String> {
        return kafkaAnswerTemplate.answer(content) {
            val map = kafkaObjectMapper.readBody(content, GroupPostCreateProjection::class.java)
            groupPostService.addGroupPost(map)
            SUCCESS
        }
    }

    @KafkaListener(topics = ["grouppost-delete-request"])
    fun `delete-grouppost-message`(content: ConsumerRecord<String, String>): Message<String>{
        return kafkaAnswerTemplate.answer(content) {
            val map = kafkaObjectMapper.readParameter(content, "postId", UUID::class.java) ?: return@answer FAILURE
            groupPostService.removeGroupPost(map)
            SUCCESS
        }
        
    }

    @KafkaListener(topics = ["grouppost-get-request"])
    @SendTo("grouppost-get-response")
    fun `get-grouppost-message`(content: ConsumerRecord<String, String>): Message<String> {
        return kafkaAnswerTemplate.answer(content) {
            val map = kafkaObjectMapper.readPathVariable(content, "grouppostId") ?: return@answer FAILURE
            val res = groupPostService.getGroupPostsById(map)
            kafkaObjectMapper.convertToMessageFromBodyObject(res)
        }


    }

    @KafkaListener(topics = ["grouppost-update-request"])
    fun `update-grouppost-message`(content: ConsumerRecord<String, String>): Message<String> {
        return kafkaAnswerTemplate.answer(content) {
            val map = kafkaObjectMapper.readPathVariable(content, "postId")
            val body = kafkaObjectMapper.readBody(content, UpdatePostProjection::class.java)
            groupPostService.editPostText(map, body.text!!)
            SUCCESS
        }
    }

    @KafkaListener(topics = ["grouppost-getall-request"])
    @SendTo("grouppost-getall-response")
    fun `grouppost-get-request`(content: ConsumerRecord<String, String>): Message<String> {
        return kafkaAnswerTemplate.answer(content) {
            val groups = kafkaObjectMapper.readParamList(content, "groupIds", UUID::class.java)
            val res = postService.loadAllPosts(FeedSearch(emptySet(), groups.toSet()))
            kafkaObjectMapper.convertToMessageFromBodyObject(res)
        }
    }

}