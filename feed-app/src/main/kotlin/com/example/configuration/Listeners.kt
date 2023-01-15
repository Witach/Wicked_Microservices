package com.example.configuration

import com.example.service.GroupServiceClientKafka
import com.example.service.PostServiceClientKafka
import com.example.service.ProfileServiceClientKafka
import com.example.servicechassis.*
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.messaging.handler.annotation.SendTo

class Listeners(val imperativeSessionStorage: ImperativeSessionStorage,
                val kafklaObjectMapper: KafkaObjectMapper,
                val postService: PostServiceClientKafka,
                val groupService: GroupServiceClientKafka,
                val profileServiceClientKafka: ProfileServiceClientKafka
) {

    @KafkaListener(topics = ["feed-loadgroup-request"])
    @SendTo("feed-loadgroup-response")
    fun `create-post-message`(record: String): String {
        imperativeSessionStorage.userId = kafklaObjectMapper.readSession(record)
        val group = kafklaObjectMapper.readPathVariable(record, "groupId")
        return tryToResponse {
            kafklaObjectMapper.convertToMessageFromBodyObject(groupService.loadGroup(group))
        }
    }


    @KafkaListener(topics = ["feed-loadgrouppost-request"])
    @SendTo("feed-loadgrouppost-response")
    fun `feed-loadgroup-request`(record: String): String {
        imperativeSessionStorage.userId = kafklaObjectMapper.readSession(record)
        val group = kafklaObjectMapper.readPathVariable(record, "groupId")
        return tryToResponse {
            kafklaObjectMapper.convertToMessageFromBodyObject(groupService.loadGroupPosts(group))
        }
    }

    @KafkaListener(topics = ["feed-loadprofilepost-request"])
    @SendTo("feed-loadprofilepost-response")
    fun `feed-loadprofilepost-request`(record: String): String {
        imperativeSessionStorage.userId = kafklaObjectMapper.readSession(record)
        val group = kafklaObjectMapper.readPathVariable(record, "profileId")
        val page = kafklaObjectMapper.readParameter(record, "page", Int::class.java) ?: 0
        val size = kafklaObjectMapper.readParameter(record, "size", Int::class.java) ?: 0
        return tryToResponse {
            kafklaObjectMapper.convertToMessageFromBodyObject(postService.loadPosts(group, page, size))
        }
    }

    @KafkaListener(topics = ["feed-search-request"])
    @SendTo("feed-search-response")
    fun `feed-search-request`(record: String): String {
        imperativeSessionStorage.userId = kafklaObjectMapper.readSession(record)
        val feedSearch = kafklaObjectMapper.readPathVariable(record, "profileId")
        val page = kafklaObjectMapper.readParameter(record, "page", Int::class.java) ?: 0
        val size = kafklaObjectMapper.readParameter(record, "size", Int::class.java) ?: 0
        return tryToResponse {
            kafklaObjectMapper.convertToMessageFromBodyObject(postService.loadPostsWitGroupPosts(feedSearch, page, size))
        }
    }

}