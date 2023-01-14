package com.example.service

import com.example.*
import com.example.applicationservice.GroupService
import com.example.applicationservice.PostService
import com.example.servicechassis.*
import org.example.EntityNotFoundException
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.messaging.handler.annotation.SendTo
import java.util.*

class PostListener(
    val kafkaObjectMapper: KafkaObjectMapper,
    val postService: PostService,
    val groupService: GroupService
) {
    @KafkaListener(topics = ["post-create-request"])
    @SendTo("post-create-response")
    fun `create-post-message`(record: String): String {
        val body = kafkaObjectMapper.readBody(record, PostCreatePorjection::class.java)
        try {
            postService.addPost(body)
        } catch (e: EntityNotFoundException) {
            return FAILURE
        }

        return SUCCESS
    }

    @KafkaListener(topics = ["post-delete-request"])
    @SendTo("post-delete-response")
    fun `delete-post-message`(record: String): String {
        val map = kafkaObjectMapper.readPathVariable(record, "profileId")
        try {
            postService.deletePost(map)
        } catch (e: EntityNotFoundException) {
            return FAILURE
        }

        return SUCCESS
    }

    @KafkaListener(topics = ["post-update-request"])
    @SendTo("post-update-response")
    fun `update-post-message`(record: String): String {
        val body = kafkaObjectMapper.readBody(record, UpdatePostProjection::class.java)
        val postId = kafkaObjectMapper.readPathVariable(record, "postId")
        try {
            postService.editPostText(postId, body)
        } catch (e: EntityNotFoundException) {
            return FAILURE
        }

        return SUCCESS
    }

    @KafkaListener(topics = ["post-deleteattachment-request"])
    @SendTo("post-deleteattachment-response")
    fun `deleteattachment-post-message`(record: String): String {
        val postId = kafkaObjectMapper.readPathVariable(record, "postId")
        val attachmentId = kafkaObjectMapper.readPathVariable(record, "attachmentId")
        try {
            postService.deletePostAttachment(postId, attachmentId)
        } catch (e: EntityNotFoundException) {
            return FAILURE
        }

        return SUCCESS
    }

    @KafkaListener(topics = ["post-addattachment-request"])
    @SendTo("post-addattachment-response")
    fun `group-exists-message`(record: String): String {
        val map = kafkaObjectMapper.readPathVariable(record, "postId")
        val res = groupService.existsById(map)
        return kafkaObjectMapper.convertToMessageFromBodyObject(res)
    }

    @KafkaListener(topics = ["post-get-request"])
    @SendTo("post-get-response")
    fun `post-get-request`(record: String): String {
        val map = kafkaObjectMapper.readBody(record, ProfileToSearchForProjection::class.java)
        val page = kafkaObjectMapper.readParameter(record, "page", Int::class.java) ?: 0
        val size = kafkaObjectMapper.readParameter(record, "size", Int::class.java) ?: 20

        return try {
            val res = postService.loadPostsPage(map, page, size)
            kafkaObjectMapper.convertToMessageFromBodyObject(res)
        } catch (e: EntityNotFoundException) {
            FAILURE
        }


    }

    @KafkaListener(topics = ["feed-get-request"])
    @SendTo("feed-get-response")
    fun `feed-get-request`(record: String): String {
        val feedSearch = kafkaObjectMapper.readBody(record, FeedSearch::class.java)
        return try {
            val res = postService.loadAllPosts(feedSearch)
            kafkaObjectMapper.convertToMessageFromBodyObject(res)
        } catch (e: EntityNotFoundException) {
            FAILURE
        }
    }
}