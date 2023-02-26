package com.example.service

import com.example.FeedSearch
import com.example.GroupPostCreateProjection
import com.example.UpdatePostProjection
import com.example.applicationservice.GroupPostService
import com.example.applicationservice.PostService
import com.example.servicechassis.FAILURE
import com.example.servicechassis.ImperativeSessionStorage
import com.example.servicechassis.KafkaObjectMapper
import com.example.servicechassis.SUCCESS
import org.example.EntityNotFoundException
import org.springframework.kafka.annotation.KafkaListener
import java.util.*

class GroupPostListener(
    val groupPostService: GroupPostService,
    val postService: PostService,
    val kafkaObjectMapper: KafkaObjectMapper,
    val imperativeSessionStorage: ImperativeSessionStorage
) {
    @KafkaListener(topics = ["grouppost-create-request"])
    fun `create-grouppost-message`(record: String): String {
        imperativeSessionStorage.userId = kafkaObjectMapper.readSession(record)
        val map = kafkaObjectMapper.readBody(record, GroupPostCreateProjection::class.java)
        try {
            groupPostService.addGroupPost(map)
        } catch (e: EntityNotFoundException) {
            return FAILURE
        }
        return SUCCESS
    }

    @KafkaListener(topics = ["grouppost-delete-request"])
    fun `delete-grouppost-message`(record: String): String {
        imperativeSessionStorage.userId = kafkaObjectMapper.readSession(record)
        val map = kafkaObjectMapper.readParameter(record, "postId", UUID::class.java) ?: return FAILURE
        try {
            groupPostService.removeGroupPost(map)
        } catch (e: EntityNotFoundException) {
            return FAILURE
        }
        return SUCCESS
    }

    @KafkaListener(topics = ["grouppost-get-request"])
    fun `get-grouppost-message`(record: String): String {
        imperativeSessionStorage.userId = kafkaObjectMapper.readSession(record)
        val map = kafkaObjectMapper.readPathVariable(record, "grouppostId") ?: return FAILURE
        return try {
            val res = groupPostService.getGroupPostsById(map)
            kafkaObjectMapper.convertToMessageFromBodyObject(res)
        } catch (e: EntityNotFoundException) {
            FAILURE
        }

    }

    @KafkaListener(topics = ["grouppost-update-request"])
    fun `update-grouppost-message`(record: String): String {
        imperativeSessionStorage.userId = kafkaObjectMapper.readSession(record)
        val map = kafkaObjectMapper.readPathVariable(record, "postId")
        val body = kafkaObjectMapper.readBody(record, UpdatePostProjection::class.java)
        try {
            groupPostService.editPostText(map, body.text!!)
        } catch (e: EntityNotFoundException) {
            return FAILURE
        }

        return SUCCESS
    }

    @KafkaListener(topics = ["grouppost-getall-request"])
    fun `grouppost-get-request`(record: String): String {
        imperativeSessionStorage.userId = kafkaObjectMapper.readSession(record)
        val groups = kafkaObjectMapper.readParamList<UUID>(record, "groupIds", UUID::class.java)
        return try {
            val res = postService.loadAllPosts(FeedSearch(emptySet(), groups.toSet()))
            kafkaObjectMapper.convertToMessageFromBodyObject(res)
        } catch (e: EntityNotFoundException) {
            FAILURE
        }
    }

}