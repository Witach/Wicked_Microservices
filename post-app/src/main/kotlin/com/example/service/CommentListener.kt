package com.example.service

import abstractcom.example.applicationservice.CommentService
import com.example.CommentCreateProjection
import com.example.ReplyCreateProjection
import com.example.ReplyEdtProjection
import com.example.servicechassis.FAILURE
import com.example.servicechassis.ImperativeSessionStorage
import com.example.servicechassis.KafkaObjectMapper
import com.example.servicechassis.SUCCESS
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.messaging.handler.annotation.SendTo

class CommentListener(val commentService: CommentService, val kafkaObjectMapper: KafkaObjectMapper, val imperativeSessionStorage: ImperativeSessionStorage) {
    @KafkaListener(topics = ["comment-create-request"])
    @SendTo("comment-create-response")
    fun `comment-create-request`(record: String): String {
        imperativeSessionStorage.userId = kafkaObjectMapper.readSession(record)
        val comment = kafkaObjectMapper.readBody(record, CommentCreateProjection::class.java)
        try {
            commentService.addComment(comment)
        } catch (e: Exception) {
            return FAILURE
        }

        return SUCCESS
    }

    @KafkaListener(topics = ["comment-delete-request"])
    @SendTo("comment-delete-response")
    fun `delete-comment-message`(record: String): String {
        imperativeSessionStorage.userId = kafkaObjectMapper.readSession(record)
        val map = kafkaObjectMapper.readPathVariable(record, "profileId")
        try {
            commentService.deleteComment(map)
        } catch (e: Exception) {
            return FAILURE
        }
        return SUCCESS
    }

    @KafkaListener(topics = ["reply-create-request"])
    @SendTo("reply-create-response")
    fun `create-commentreply-message`(record: String): String {
        imperativeSessionStorage.userId = kafkaObjectMapper.readSession(record)
        val map = kafkaObjectMapper.readPathVariable(record, "commentId")
        val body = kafkaObjectMapper.readBody(record, ReplyCreateProjection::class.java)
        try {
            commentService.addReply(map, body)
        } catch (e: Exception) {
            return FAILURE
        }
        return SUCCESS
    }

    @KafkaListener(topics = ["reply-edit-request"])
    @SendTo("reply-edit-response")
    fun `update-commentreply-message`(record: String): String {
        imperativeSessionStorage.userId = kafkaObjectMapper.readSession(record)
        val map = kafkaObjectMapper.readPathVariable(record, "commentId")
        try {
            commentService.editReply(map, kafkaObjectMapper.readBody(record, ReplyEdtProjection::class.java))
        } catch (e: Exception) {
            return FAILURE
        }
        return SUCCESS
    }

    @KafkaListener(topics = ["reply-delete-request"])
    @SendTo("reply-delete-response")
    fun `delete-commentreply-message`(record: String): String {
        imperativeSessionStorage.userId = kafkaObjectMapper.readSession(record)
        val map = kafkaObjectMapper.readPathVariable(record, "commentId")
        val reply = kafkaObjectMapper.readPathVariable(record, "replyId")
        try {
            commentService.removeReply(map, reply)
        } catch (e: Exception) {
            return FAILURE
        }
        return SUCCESS
    }

}