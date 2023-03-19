package com.example.service

import abstractcom.example.applicationservice.CommentService
import com.example.CommentCreateProjection
import com.example.ReplyCreateProjection
import com.example.ReplyEdtProjection
import com.example.servicechassis.*
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.messaging.Message
import org.springframework.messaging.handler.annotation.SendTo

class CommentListener(val commentService: CommentService,
                      val kafkaObjectMapper: KafkaObjectMapper,
                      val kafkaAnswerTemplate: KafkaAnswerTemplate) {
    @KafkaListener(topics = ["comment-create-request"])
    @SendTo("comment-create-response")
    fun `comment-create-request`(content: ConsumerRecord<String, String>): Message<String> {
        return kafkaAnswerTemplate.answer(content) {
            val comment = kafkaObjectMapper.readBody(content, CommentCreateProjection::class.java)
            commentService.addComment(comment)
            SUCCESS
        }
    }

    @KafkaListener(topics = ["comment-delete-request"])
    @SendTo("comment-delete-response")
    fun `delete-comment-message`(content: ConsumerRecord<String, String>): Message<String> {
        return kafkaAnswerTemplate.answer(content) {
            val map = kafkaObjectMapper.readPathVariable(content, "profileId")
            commentService.deleteComment(map)
            SUCCESS
        }
    }

    @KafkaListener(topics = ["reply-create-request"])
    @SendTo("reply-create-response")
    fun `create-commentreply-message`(content: ConsumerRecord<String, String>): Message<String> {
        return kafkaAnswerTemplate.answer(content) {
            val map = kafkaObjectMapper.readPathVariable(content, "commentId")
            val body = kafkaObjectMapper.readBody(content, ReplyCreateProjection::class.java)
            commentService.addReply(map, body)
            SUCCESS
        }
    }

    @KafkaListener(topics = ["reply-edit-request"])
    @SendTo("reply-edit-response")
    fun `update-commentreply-message`(record: ConsumerRecord<String, String>): Message<String> {
        return kafkaAnswerTemplate.answer(record) {
            val map = kafkaObjectMapper.readPathVariable(record, "commentId")
            commentService.editReply(map, kafkaObjectMapper.readBody(record, ReplyEdtProjection::class.java))
            SUCCESS
        }
    }

    @KafkaListener(topics = ["reply-delete-request"])
    @SendTo("reply-delete-response")
    fun `delete-commentreply-message`(record: ConsumerRecord<String, String>): Message<String> {
        return kafkaAnswerTemplate.answer(record) {
            val map = kafkaObjectMapper.readPathVariable(record, "commentId")
            val reply = kafkaObjectMapper.readPathVariable(record, "replyId")
            commentService.removeReply(map, reply)
            SUCCESS
        }
    }

}