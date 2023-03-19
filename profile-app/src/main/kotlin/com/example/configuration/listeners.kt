package com.example.configuration

import com.example.CreateUserProjection
import com.example.ProfileEditProjection
import com.example.ProfileProjectionWithFollow
import com.example.UserEditProjection
import com.example.service.ProfileService
import com.example.service.UserService
import com.example.servicechassis.*
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.messaging.Message
import org.springframework.messaging.handler.annotation.SendTo

open class Listeners(val kafkaObjectMapper: KafkaObjectMapper,
                val imperativeSessionStorage: ImperativeSessionStorage,
                val profileService: ProfileService,
                val userService: UserService,
                val kafkaAnswerTemplate: KafkaAnswerTemplate) {

    @KafkaListener(topics = ["profile-update-request"])
    @SendTo("profile-update-response")
    fun `profile-update-message`(content: ConsumerRecord<String, String>): Message<String> {
        return kafkaAnswerTemplate.answer(content) {
            val map = kafkaObjectMapper.readPathVariable(content, "profileId")
            val body = kafkaObjectMapper.readBody(content, ProfileEditProjection::class.java)
            profileService.editProfile(map, body)
            SUCCESS
        }
    }

    @KafkaListener(topics = ["profile-addtogroup-request"])
    @SendTo("profile-addtogroup-response")
    fun `profile-addtogroup-message`(content: ConsumerRecord<String, String>): Message<String>  =
        kafkaAnswerTemplate.answer(content) {
            val map = kafkaObjectMapper.readPathVariable(content, "profileId")
            val group = kafkaObjectMapper.readParameterUUID(content, "groupId")
            profileService.addToGroup(map, group)
            SUCCESS
        }

    @KafkaListener(topics = ["profile-removefromgroup-request"])
    @SendTo("profile-removefromgroup-response")
    fun `profile-removefromgroup-message`(content: ConsumerRecord<String, String>): Message<String> {
        return kafkaAnswerTemplate.answer(content) {
            val map = kafkaObjectMapper.readPathVariable(content, "profileId")
            val group = kafkaObjectMapper.readParameterUUID(content, "groupId")
            profileService.removeFromGroup(map, group)
            SUCCESS
        }
    }

    @KafkaListener(topics = ["profile-starttofollow-request"])
    @SendTo("profile-starttofollow-response")
    fun `profile-starttofollow-message`(content: ConsumerRecord<String, String>): Message<String> {
        return kafkaAnswerTemplate.answer(content) {
            val map = kafkaObjectMapper.readPathVariable(content, "profileId")
            val body = kafkaObjectMapper.readBody(content, ProfileProjectionWithFollow::class.java)
            profileService.startToFollow(map, body)
            SUCCESS
        }
    }

    @KafkaListener(topics = ["profile-stoptofollow-request"])
    @SendTo("profile-stoptofollow-response")
    fun `profile-stoptofollow-message`(content: ConsumerRecord<String, String>): Message<String> {
        return kafkaAnswerTemplate.answer(content) {
            val map = kafkaObjectMapper.readPathVariable(content, "profileId")
            val body = kafkaObjectMapper.readParameterUUID(content, "profileToFollow")
            profileService.removeFollowedProfile(map, body)
            SUCCESS
        }
    }

    @KafkaListener(topics = ["user-creat-request"])
    @SendTo("user-creat-response")
    fun `user-creat-message`(content: ConsumerRecord<String, String>): Message<String> {
        return kafkaAnswerTemplate.answer(content) {
            val body = kafkaObjectMapper.readBody(content.value(), CreateUserProjection::class.java)
            userService.registerNewUser(body)
            SUCCESS
        }
    }

    @KafkaListener(topics = ["user-delete-request"])
    @SendTo("user-delete-response")
    fun `user-delete-message`(content: ConsumerRecord<String, String>): Message<String> {
        return kafkaAnswerTemplate.answer(content) {
            val map = kafkaObjectMapper.readPathVariable(content, "userId")
            userService.removeUser(map)
            SUCCESS
        }
    }

    @KafkaListener(topics = ["user-update-request"])
    @SendTo("user-update-response")
    fun `user-update-message`(content: ConsumerRecord<String, String>): Message<String> {
        return kafkaAnswerTemplate.answer(content) {
            val map = kafkaObjectMapper.readPathVariable(content, "userId")
            val body = kafkaObjectMapper.readBody(content, UserEditProjection::class.java)
            userService.editUser(map, body)
            SUCCESS
        }
    }

    @KafkaListener(topics = ["profile-getall-request"])
    @SendTo("profile-getall-response")
    fun `profile-getall-request`(content: ConsumerRecord<String, String>): Message<String> {
        return kafkaAnswerTemplate.answer(content) {
            kafkaObjectMapper.convertToMessageFromBodyObject(
                profileService.fetchAllProfiles()
            )
        }
    }

    @KafkaListener(topics = ["profile-get-request"])
    @SendTo("profile-get-response")
    fun `profile-get-request`(content: ConsumerRecord<String, String>): Message<String>  {
        return kafkaAnswerTemplate.answer(content) {
            val map = kafkaObjectMapper.readPathVariable(content, "profileId")
            kafkaObjectMapper.convertToMessageFromBodyObject(
                profileService.fetchUserProfile(map)
            )
        }
    }

    @KafkaListener(topics = ["user-get-request"])
    @SendTo("user-get-response")
    fun `user-get-request`(content: ConsumerRecord<String, String>): Message<String> {
        return kafkaAnswerTemplate.answer(content) {
            val map = kafkaObjectMapper.readPathVariable(content, "userId")
            kafkaObjectMapper.convertToMessageFromBodyObject(
                userService.getUser(map)
            )
        }
    }

    @KafkaListener(topics = ["user-getall-request"])
    @SendTo("user-getall-response")
    fun `user-getall-request`(content: ConsumerRecord<String, String>): Message<String>{
        return kafkaAnswerTemplate.answer(content) {
            kafkaObjectMapper.convertToMessageFromBodyObject(
                userService.getAllUsers()
            )
        }
    }
}

