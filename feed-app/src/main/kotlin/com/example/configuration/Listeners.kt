package com.example.configuration

import com.example.service.GroupServiceClientKafka
import com.example.service.PostServiceClientKafka
import com.example.service.ProfileServiceClientKafka
import com.example.servicechassis.ImperativeSessionStorage
import com.example.servicechassis.KafkaObjectMapper
import com.example.servicechassis.tryToResponse
import org.springframework.kafka.annotation.KafkaListener

class Listeners(val imperativeSessionStorage: ImperativeSessionStorage,
                val kafklaObjectMapper: KafkaObjectMapper,
                val postService: PostServiceClientKafka,
                val groupService: GroupServiceClientKafka,
                val profileServiceClientKafka: ProfileServiceClientKafka
) {

    @KafkaListener(topics = ["feed-loadgroup-request"])
    fun `create-post-message`(record: String): String {
        imperativeSessionStorage.userId = kafklaObjectMapper.readSession(record)
        val group = kafklaObjectMapper.readPathVariable(record, "groupId")
        return tryToResponse {
            kafklaObjectMapper.convertToMessageFromBodyObject(groupService.loadGroup(group))
        }
    }


    @KafkaListener(topics = ["feed-loadgrouppost-request"])
    fun `feed-loadgroup-request`(record: String): String {
        imperativeSessionStorage.userId = kafklaObjectMapper.readSession(record)
        val group = kafklaObjectMapper.readPathVariable(record, "groupId")
        return tryToResponse {
            kafklaObjectMapper.convertToMessageFromBodyObject(groupService.loadGroupPosts(group))
        }
    }

    @KafkaListener(topics = ["feed-loadprofilepost-request"])
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