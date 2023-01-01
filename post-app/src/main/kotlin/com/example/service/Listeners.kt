package com.example.service

import abstractcom.example.applicationservice.CommentService
import com.example.*
import com.example.applicationservice.GroupPostService
import com.example.applicationservice.GroupService
import com.example.applicationservice.PostService
import com.example.servicechassis.KafkaClient
import com.fasterxml.jackson.databind.ObjectMapper
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service
import java.util.*

@Service
class Listeners(val objectMapper: ObjectMapper,
                val commentService: CommentService,
                val groupPostService: GroupPostService,
                val groupService: GroupService,
                val postService: PostService,
                val kafkaClient: KafkaClient) {

    @KafkaListener(topics = ["delete-comment-message"], groupId = "delete-comment-message-consumer")
    fun `delete-comment-message`(record: ConsumerRecord<String, String>) {
        val map = objectMapper.readValue(record.value(), Map::class.java)
        commentService.deleteComment(UUID.fromString(map["commentId"] as String))
    }

    @KafkaListener(topics = ["create-commentreply-message"], groupId = "create-commentreply-message-consumer")
    fun `create-commentreply-message`(record: ConsumerRecord<String, String>) {
        val map = objectMapper.readValue(record.value(), Map::class.java)
        commentService.addReply(UUID.fromString(map["commentId"] as String), objectMapper.readValue(map["reply"] as String,
            ReplyCreateProjection::class.java))
    }

    @KafkaListener(topics = ["update-commentreply-message"], groupId = "update-commentreply-message-consumer")
    fun `update-commentreply-message`(record: ConsumerRecord<String, String>) {
        val map = objectMapper.readValue(record.value(), Map::class.java)
        commentService.editReply(UUID.fromString(map["commentId"] as String),
            objectMapper.readValue(map["reply"] as String, ReplyEdtProjection::class.java))
    }

    @KafkaListener(topics = ["delete-commentreply-message"], groupId = "delete-commentreply-message-consumer")
    fun `delete-commentreply-message`(record: ConsumerRecord<String, String>) {
        val map = objectMapper.readValue(record.value(), Map::class.java)
        commentService.removeReply(UUID.fromString(map["commentId"] as String),
            UUID.fromString(map["replyId"] as String))
    }

    @KafkaListener(topics = ["create-grouppost-message"], groupId = "create-grouppost-message-consumer")
    fun `create-grouppost-message`(record: ConsumerRecord<String, String>) {
        val map = objectMapper.readValue(record.value(), Map::class.java)
        groupPostService.addGroupPost(objectMapper.readValue(map["post"] as String, GroupPostCreateProjection::class.java))
    }

    @KafkaListener(topics = ["delete-grouppost-message"], groupId = "delete-grouppost-message-consumer")
    fun `delete-grouppost-message`(record: ConsumerRecord<String, String>) {
        val map = objectMapper.readValue(record.value(), Map::class.java)
        groupPostService.removeGroupPost(UUID.fromString(map["postId"] as String))
    }

    @KafkaListener(topics = ["get-grouppost-message"], groupId = "get-grouppost-message-consumer")
    fun `get-grouppost-message`(record: ConsumerRecord<String, String>) {
        val map = objectMapper.readValue(record.value(), Map::class.java)
        val res = groupPostService.getGroupPosts(UUID.fromString(map["postId"] as String))
        kafkaClient.send("get-grouppost-response",  record.key(), mapOf("group" to res));
    }

    @KafkaListener(topics = ["update-grouppost-message"], groupId = "update-grouppost-message-consumer")
    fun `update-grouppost-message`(record: ConsumerRecord<String, String>) {
        val map = objectMapper.readValue(record.value(), Map::class.java)
        groupPostService.editPostText(UUID.fromString(map["postId"] as String), map["postText"] as String)
    }

    @KafkaListener(topics = ["create-group-message"], groupId = "create-group-message-consumer")
    fun `create-group-message`(record: ConsumerRecord<String, String>) {
        val map = objectMapper.readValue(record.value(), Map::class.java)
        groupService.addGroup(objectMapper.readValue(map["groupName"] as String, GroupCreateProjection::class.java))
    }

    @KafkaListener(topics = ["delete-group-message"], groupId = "delete-group-message-consumer")
    fun `delete-group-message`(record: ConsumerRecord<String, String>) {
        val map = objectMapper.readValue(record.value(), Map::class.java)
        groupService.removeGroup(UUID.fromString(map["groupId"] as String))

    }

    @KafkaListener(topics = ["addprofile-group-message"], groupId = "addprofile-group-message-consumer")
    fun `addprofile-group-message`(record: ConsumerRecord<String, String>) {
        val map = objectMapper.readValue(record.value(), Map::class.java)
        groupService.addProfile(UUID.fromString(map["groupId"] as String), UUID.fromString(map["profileId"] as String))
    }

    @KafkaListener(topics = ["removeprofile-group-message"], groupId = "removeprofile-group-message-consumer")
    fun `removeprofile-group-message`(record: ConsumerRecord<String, String>) {
        val map = objectMapper.readValue(record.value(), Map::class.java)
        groupService.removeProfile(UUID.fromString(map["groupId"] as String), UUID.fromString(map["profileId"] as String))
    }

    @KafkaListener(topics = ["create-post-message"], groupId = "create-post-message-consumer")
    fun `create-post-message`(record: ConsumerRecord<String, String>) {
        val map = objectMapper.readValue(record.value(), Map::class.java)
        postService.addPost(objectMapper.readValue(map["post"] as String, PostCreatePorjection::class.java))
    }

    @KafkaListener(topics = ["get-post-message"], groupId = "get-post-message-consumer")
    fun `get-post-message`(record: ConsumerRecord<String, String>) {
        val map = objectMapper.readValue(record.value(), Map::class.java)
        val res = postService.loadPost(UUID.fromString(map["postId"] as String))
        kafkaClient.send("get-post-response", record.key(), mapOf("post" to res))
    }

    @KafkaListener(topics = ["delete-post-message"], groupId = "delete-post-message-consumer")
    fun `delete-post-message`(record: ConsumerRecord<String, String>) {
        val map = objectMapper.readValue(record.value(), Map::class.java)
        postService.deletePost(UUID.fromString(map["postId"] as String))
    }

    @KafkaListener(topics = ["update-post-message"], groupId = "update-post-message-consumer")
    fun `update-post-message`(record: ConsumerRecord<String, String>) {
        val map = objectMapper.readValue(record.value(), Map::class.java)
        postService.editPostText(UUID.fromString(map["postId"] as String), map["postText"] as String)
    }

    @KafkaListener(topics = ["deleteattachment-post-message"], groupId = "deleteattachment-post-message-consumer")
    fun `deleteattachment-post-message`(record: ConsumerRecord<String, String>) {
        val map = objectMapper.readValue(record.value(), Map::class.java)
        postService.deletePostAttachment(UUID.fromString(map["postId"] as String),
            UUID.fromString(map["attachmentId"] as String))
    }

    @KafkaListener(topics = ["group-exists-message"], groupId = "group-exists-message-consumer")
    fun `group-exists-message`(record: ConsumerRecord<String, String>) {
        val map = objectMapper.readValue(record.value(), Map::class.java)
        val res = groupService.existsById(UUID.fromString(map["groupId"] as String))
        kafkaClient.send("group-exists-response", record.key(), mapOf( "groupExists" to res))
    }
}