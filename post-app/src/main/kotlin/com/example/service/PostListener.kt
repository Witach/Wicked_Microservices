package com.example.service

import com.example.FeedSearch
import com.example.PostCreatePorjection
import com.example.ProfileToSearchForProjection
import com.example.UpdatePostProjection
import com.example.applicationservice.GroupService
import com.example.applicationservice.PostService
import com.example.servicechassis.*
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.messaging.Message
import org.springframework.messaging.handler.annotation.SendTo

class PostListener(
    val kafkaObjectMapper: KafkaObjectMapper,
    val postService: PostService,
    val groupService: GroupService,
    val kafkaAnswerTemplate: KafkaAnswerTemplate
) {
    @KafkaListener(topics = ["post-create-request"])
    @SendTo("post-create-response")
    fun `create-post-message`(content: ConsumerRecord<String, String>): Message<String> {
        return kafkaAnswerTemplate.answer(content) {
            val body = kafkaObjectMapper.readBody(content, PostCreatePorjection::class.java)
            postService.addPost(body)
            SUCCESS
        }
    }

    @KafkaListener(topics = ["post-delete-request"])
    @SendTo("post-delete-response")
    fun `delete-post-message`(content: ConsumerRecord<String, String>): Message<String> {
        return kafkaAnswerTemplate.answer(content) {
            val map = kafkaObjectMapper.readPathVariable(content, "profileId")
            postService.deletePost(map)
            SUCCESS
        }
    }

    @KafkaListener(topics = ["post-update-request"])
    @SendTo("post-update-response")
    fun `update-post-message`(content: ConsumerRecord<String, String>): Message<String> {
        return kafkaAnswerTemplate.answer(content) {
            val body = kafkaObjectMapper.readBody(content, UpdatePostProjection::class.java)
            val postId = kafkaObjectMapper.readPathVariable(content, "postId")
            postService.editPostText(postId, body)
            SUCCESS
        }
    }

    @KafkaListener(topics = ["post-deleteattachment-request"])
    @SendTo("post-deleteattachment-response")
    fun `deleteattachment-post-message`(content: ConsumerRecord<String, String>): Message<String> {
        return kafkaAnswerTemplate.answer(content) {
            val postId = kafkaObjectMapper.readPathVariable(content, "postId")
            val attachmentId = kafkaObjectMapper.readPathVariable(content, "attachmentId")
            postService.deletePostAttachment(postId, attachmentId)
            SUCCESS
        }
    }

    @KafkaListener(topics = ["post-addattachment-request"])
    fun `group-exists-message`(content: ConsumerRecord<String, String>): Message<String> {
        return kafkaAnswerTemplate.answer(content) {
            val map = kafkaObjectMapper.readPathVariable(content, "postId")
            val res = groupService.existsById(map)
            kafkaObjectMapper.convertToMessageFromBodyObject(res)
        }
    }

    @KafkaListener(topics = ["post-get-request"])
    @SendTo("post-get-response")
    fun `post-get-request`(content: ConsumerRecord<String, String>): Message<String>  {
        return kafkaAnswerTemplate.answer(content) {
            val map = kafkaObjectMapper.readBody(content, ProfileToSearchForProjection::class.java)
            val page = kafkaObjectMapper.readParameter(content, "page", Int::class.java) ?: 0
            val size = kafkaObjectMapper.readParameter(content, "size", Int::class.java) ?: 20
            val res = postService.loadPostsPage(map, page, size)
            kafkaObjectMapper.convertToMessageFromBodyObject(res)
        }

    }

    @KafkaListener(topics = ["post-getall-request"])
    @SendTo("post-getall-response")
    fun `feed-get-request`(content: ConsumerRecord<String, String>): Message<String> {
        return kafkaAnswerTemplate.answer(content) {
            val feedSearch = kafkaObjectMapper.readBody(content, FeedSearch::class.java)
            val res = postService.loadAllPosts(feedSearch)
            kafkaObjectMapper.convertToMessageFromBodyObject(res)
        }
    }
}