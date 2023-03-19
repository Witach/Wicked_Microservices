package com.example.service

import com.example.*
import com.example.domainservice.GroupServiceClient
import com.example.domainservice.PostServiceClient
import com.example.domainservice.ProfileServiceClient
import com.example.servicechassis.*
import com.fasterxml.jackson.core.type.TypeReference
import org.springframework.context.annotation.Profile
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate
import org.springframework.stereotype.Component
import java.util.*

@Profile("kafka")
@Component
class GroupServiceClientKafka(val replyingKafkaTemplate: ReplyingKafkaTemplate<String, String, String>,
                              val kafkaObjectMapper: KafkaObjectMapper,
                              val sessionStorage: ImperativeSessionStorage,
                              val profileServiceClientKafka: ProfileServiceClientKafka ): GroupServiceClient {
    override fun loadGroup(groupId: UUID): GroupProjection {
        val response = kafkaProxyFeign {
            kafkaTemplate = replyingKafkaTemplate
            requestTopic = "group-get-request"
            post = kafkaObjectMapper.convertToMessageFromPathVariable("groupId" to groupId.toString())
        }()
        return kafkaObjectMapper.readBody(response, GroupProjection::class.java)
    }

    override fun loadGroupPosts(groupId: UUID): List<PostProjection> {
        return sendFeed(
            FeedSearch(
            profiles = mutableSetOf(),
            groups = mutableSetOf(groupId)
            )
        )
    }

    override fun loadPosts(profile: UUID, page: Int, size: Int): List<PostProjection> {
        return sendFeed(
            FeedSearch(
            profiles = mutableSetOf(profile),
            groups = mutableSetOf()
            ), page, size)
    }

    override fun loadPostsWitGroupPosts(page: Int, size: Int): List<PostProjection> {

        val profileData = profileServiceClientKafka.loadProfileData(sessionStorage.sessionOwner.userId!!)
        return sendFeed(
            FeedSearch(
            profiles = profileData.followed,
            groups = profileData.groups
            ), page, size)
    }

    fun sendFeed(feedSearch: FeedSearch, page: Int = 0, size: Int = 20): List<PostProjection> {
        val response = kafkaProxyFeign {
            kafkaTemplate = replyingKafkaTemplate
            requestTopic = "post-getall-request"
            post = kafkaObjectMapper.convertToMessageFrom {
                body = feedSearch
                param = mapOf(PAGE to page, SIZE to size)
            }
        }()
        val body = kafkaObjectMapper.objectMapper.readTree(response)[BODY_KEY].toString()
        val typeRef = object : TypeReference<List<PostProjection>>() {}
        return kafkaObjectMapper.objectMapper.readValue(body, typeRef)
    }

}

@Profile("kafka")
@Component
class PostServiceClientKafka(val groupServiceClientKafka: GroupServiceClientKafka): PostServiceClient {
    override fun loadPosts(profile: UUID, page: Int, size: Int): List<PostProjection> {
        return groupServiceClientKafka.sendFeed(
            FeedSearch(
            profiles = mutableSetOf(profile),
            groups = mutableSetOf()
            ), page, size)
    }

    override fun loadPostsWitGroupPosts(profile: UUID, page: Int, size: Int): List<PostProjection> {
        return groupServiceClientKafka.sendFeed(
            FeedSearch(
                profiles = mutableSetOf(profile),
                groups = mutableSetOf()
            ), page, size)
    }
}

@Profile("kafka")
@Component
class ProfileServiceClientKafka(val replyingKafkaTemplate: ReplyingKafkaTemplate<String, String, String>, val kafkaObjectMapper: KafkaObjectMapper): ProfileServiceClient {
    override fun loadProfileData(profile: UUID): ProfileProjection {
        val response = kafkaProxyFeign {
            kafkaTemplate = replyingKafkaTemplate
            requestTopic = "profile-get-request"
            post = kafkaObjectMapper.convertToMessageFromPathVariable("profileId" to profile.toString())
        }()
        return kafkaObjectMapper.readBody(response, ProfileProjection::class.java)
    }
}