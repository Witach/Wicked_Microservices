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
import org.springframework.messaging.handler.annotation.SendTo
import java.util.*

class Listeners(val objectMapper: ObjectMapper,
                val commentService: CommentService,
                val groupPostService: GroupPostService,
                val groupService: GroupService,
                val postService: PostService,
                val kafkaClient: KafkaClient) {

    @KafkaListener(topics = ["comment-delete-request"])
    @SendTo("comment-delete-response")
    fun `delete-comment-message`(record: ConsumerRecord<String, String>): String {
        val map = objectMapper.readValue(record.value(), Map::class.java)
        commentService.deleteComment(UUID.fromString(map["commentId"] as String))
        return "success"
    }

    @KafkaListener(topics = ["reply-create-request"])
    @SendTo("reply-create-response")
    fun `create-commentreply-message`(record: ConsumerRecord<String, String>): String {
        val map = objectMapper.readValue(record.value(), Map::class.java)
        commentService.addReply(UUID.fromString(map["commentId"] as String), objectMapper.readValue(map["reply"] as String,
            ReplyCreateProjection::class.java))
        return "success"
    }

    @KafkaListener(topics = ["reply-edit-request"])
    @SendTo("reply-edit-response")
    fun `update-commentreply-message`(record: ConsumerRecord<String, String>): String {
        val map = objectMapper.readValue(record.value(), Map::class.java)
        commentService.editReply(UUID.fromString(map["commentId"] as String),
            objectMapper.readValue(map["reply"] as String, ReplyEdtProjection::class.java))
        return "success"
    }

    @KafkaListener(topics = ["reply-delete-request"])
    @SendTo("reply-delete-response")
    fun `delete-commentreply-message`(record: ConsumerRecord<String, String>): String {
        val map = objectMapper.readValue(record.value(), Map::class.java)
        commentService.removeReply(UUID.fromString(map["commentId"] as String),
            UUID.fromString(map["replyId"] as String))
        return "success"
    }

    @KafkaListener(topics = ["grouppost-create-request"])
    @SendTo("grouppost-create-response")
    fun `create-grouppost-message`(record: ConsumerRecord<String, String>): String {
        val map = objectMapper.readValue(record.value(), Map::class.java)
        groupPostService.addGroupPost(objectMapper.readValue(map["post"] as String, GroupPostCreateProjection::class.java))
        return "success"
    }

    @KafkaListener(topics = ["grouppost-delete-request"])
    @SendTo("grouppost-delete-response")
    fun `delete-grouppost-message`(record: ConsumerRecord<String, String>): String {
        val map = objectMapper.readValue(record.value(), Map::class.java)
        groupPostService.removeGroupPost(UUID.fromString(map["postId"] as String))
        return "success"
    }

    @KafkaListener(topics = ["grouppost-get-request"])
    @SendTo("grouppost-get-response")
    fun `get-grouppost-message`(record: ConsumerRecord<String, String>): String {
        val map = objectMapper.readValue(record.value(), Map::class.java)
        val res = groupPostService.getGroupPostsById(UUID.fromString(map["postId"] as String))
        kafkaClient.send("get-grouppost-response",  record.key(), mapOf("group" to res));
        return "success"
    }

    @KafkaListener(topics = ["grouppost-update-request"])
    @SendTo("grouppost-update-response")
    fun `update-grouppost-message`(record: ConsumerRecord<String, String>): String {
        val map = objectMapper.readValue(record.value(), Map::class.java)
        groupPostService.editPostText(UUID.fromString(map["postId"] as String), map["postText"] as String)
        return "success"
    }

    @KafkaListener(topics = ["group-create-request"])
    @SendTo("group-create-response")
    fun `create-group-message`(record: ConsumerRecord<String, String>): String {
        val map = objectMapper.readValue(record.value(), Map::class.java)
        groupService.addGroup(objectMapper.readValue(map["groupName"] as String, GroupCreateProjection::class.java))
        return "success"
    }

    @KafkaListener(topics = ["group-delete-request"])
    @SendTo("group-delete-response")
    fun `delete-group-message`(record: ConsumerRecord<String, String>): String {
        val map = objectMapper.readValue(record.value(), Map::class.java)
        groupService.removeGroup(UUID.fromString(map["groupId"] as String))
        return "success"
    }

    @KafkaListener(topics = ["group-profileadd-request"])
    @SendTo("group-profileadd-response")
    fun `addprofile-group-message`(record: ConsumerRecord<String, String>): String {
        val map = objectMapper.readValue(record.value(), Map::class.java)
        groupService.addProfile(ProfileProjectionWithFollow(UUID.fromString(map["groupId"] as String)),
            UUID.fromString(map["profileId"] as String))
        return "success"
    }

    @KafkaListener(topics = ["group-profileremove-request"])
    @SendTo("group-profileremove-response")
    fun `removeprofile-group-message`(record: ConsumerRecord<String, String>): String {
        val map = objectMapper.readValue(record.value(), Map::class.java)
        groupService.removeProfile(UUID.fromString(map["groupId"] as String), UUID.fromString(map["profileId"] as String))
        return "success"
    }

    @KafkaListener(topics = ["post-create-request"])
    @SendTo("post-create-response")
    fun `create-post-message`(record: ConsumerRecord<String, String>): String {
        val map = objectMapper.readValue(record.value(), Map::class.java)
        postService.addPost(objectMapper.readValue(map["post"] as String, PostCreatePorjection::class.java))
        return "success"
    }

    @KafkaListener(topics = ["post-delete-request"])
    @SendTo("post-delete-response")
    fun `delete-post-message`(record: ConsumerRecord<String, String>): String {
        val map = objectMapper.readValue(record.value(), Map::class.java)
        postService.deletePost(UUID.fromString(map["postId"] as String))
        return "success"
    }

    @KafkaListener(topics = ["post-update-request"])
    @SendTo("post-update-response")
    fun `update-post-message`(record: ConsumerRecord<String, String>): String {
        val map = objectMapper.readValue(record.value(), Map::class.java)
        postService.editPostText(UUID.fromString(map["postId"] as String), UpdatePostProjection(map["postText"] as String))
        return "success"
    }

    @KafkaListener(topics = ["post-deleteattachment-request"])
    @SendTo("post-deleteattachment-response")
    fun `deleteattachment-post-message`(record: ConsumerRecord<String, String>): String {
        val map = objectMapper.readValue(record.value(), Map::class.java)
        postService.deletePostAttachment(UUID.fromString(map["postId"] as String),
            UUID.fromString(map["attachmentId"] as String))
        return "success"
    }

    @KafkaListener(topics = ["post-addattachment-request"])
    @SendTo("post-addattachment-response")
    fun `group-exists-message`(record: ConsumerRecord<String, String>): Map<String, Any> {
        val map = objectMapper.readValue(record.value(), Map::class.java)
        val res = groupService.existsById(UUID.fromString(map["groupId"] as String))
        return mapOf("exists" to res)
    }

    @KafkaListener(topics = ["group-get-request"])
    @SendTo("group-get-response")
    fun `group-get-response`(record: ConsumerRecord<String, String>): Map<String, Any> {
        val map = objectMapper.readValue(record.value(), Map::class.java)
        val res = groupService.fetchGroup(GroupId(UUID.fromString(map["groupId"] as String)))
        return mapOf("group" to res)
    }

    @KafkaListener(topics = ["grouppost-get-request"])
    @SendTo("grouppost-get-response")
    fun `grouppost-get-request`(record: ConsumerRecord<String, String>): Map<String, Any> {
        val map = objectMapper.readValue(record.value(), Map::class.java)
        val feedSearch = objectMapper.readValue(map["feedSearch"] as String, FeedSearch::class.java)
        val res = postService.loadAllPosts(feedSearch)
        return mapOf("group" to res)
    }

    @KafkaListener(topics = ["post-get-request"])
    @SendTo("post-get-response")
    fun `post-get-request`(record: ConsumerRecord<String, String>): Map<String, Any> {
        val map = objectMapper.readValue(record.value(), Map::class.java)
        val feedSearch = objectMapper.readValue(map["profileToSearchForProjection"] as String,
            ProfileToSearchForProjection::class.java)
        val res = postService.loadPostsPage(feedSearch)
        return mapOf("group" to res)
    }
}