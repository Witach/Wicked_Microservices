package com.example.configuration

import com.example.CreateUserProjection
import com.example.ProfileEditProjection
import com.example.ProfileProjectionWithFollow
import com.example.UserEditProjection
import com.example.service.ProfileService
import com.example.service.UserService
import com.example.servicechassis.*
import org.example.EntityNotFoundException
import org.springframework.kafka.annotation.KafkaListener

class Listeners(val kafkaObjectMapper: KafkaObjectMapper,
                val imperativeSessionStorage: ImperativeSessionStorage,
                val profileService: ProfileService,
                val userService: UserService) {

    @KafkaListener(topics = ["profile-update-request"])
    fun `profile-update-message`(content: String): String {
        imperativeSessionStorage.userId = kafkaObjectMapper.readSession(content)
        val map = kafkaObjectMapper.readPathVariable(content, "profileId")
        val body = kafkaObjectMapper.readBody(content, ProfileEditProjection::class.java)
        return try {
            profileService.editProfile(map, body)
            SUCCESS
        } catch (e: EntityNotFoundException) {
            FAILURE
        }
    }

    @KafkaListener(topics = ["profile-addtogroup-request"])
    fun `profile-addtogroup-message`(content: String): String {
        imperativeSessionStorage.userId = kafkaObjectMapper.readSession(content)
        val map = kafkaObjectMapper.readPathVariable(content, "profileId")
        val group = kafkaObjectMapper.readParameterUUID(content, "groupId")
        return tryToResponse {
            profileService.addToGroup(map, group)
            SUCCESS
        }
    }

    @KafkaListener(topics = ["profile-removefromgroup-request"])
    fun `profile-removefromgroup-message`(content: String): String {
        imperativeSessionStorage.userId = kafkaObjectMapper.readSession(content)
        val map = kafkaObjectMapper.readPathVariable(content, "profileId")
        val group = kafkaObjectMapper.readParameterUUID(content, "groupId")
        return tryToResponse {
            profileService.removeFromGroup(map, group)
            SUCCESS
        }

    }

    @KafkaListener(topics = ["profile-starttofollow-request"])
    fun `profile-starttofollow-message`(content: String): String {
        imperativeSessionStorage.userId = kafkaObjectMapper.readSession(content)
        val map = kafkaObjectMapper.readPathVariable(content, "profileId")
        val body = kafkaObjectMapper.readBody(content, ProfileProjectionWithFollow::class.java)
        return tryToResponse {
            profileService.startToFollow(map, body)
            SUCCESS
        }
    }

    @KafkaListener(topics = ["profile-stoptofollow-request"])
    fun `profile-stoptofollow-message`(content: String): String {
        imperativeSessionStorage.userId = kafkaObjectMapper.readSession(content)
        val map = kafkaObjectMapper.readPathVariable(content, "profileId")
        val body = kafkaObjectMapper.readParameterUUID(content, "profileToFollow")
        return tryToResponse {
            profileService.removeFollowedProfile(map, body)
            SUCCESS
        }
    }

    @KafkaListener(topics = ["user-creat-request"])
    fun `user-creat-message`(content: String): String {
        imperativeSessionStorage.userId = kafkaObjectMapper.readSession(content)
        val body = kafkaObjectMapper.readBody(content, CreateUserProjection::class.java)
        return tryToResponse {
            userService.registerNewUser(body)
            SUCCESS
        }
    }

    @KafkaListener(topics = ["user-delete-request"])
    fun `user-delete-message`(content: String): String {
        imperativeSessionStorage.userId = kafkaObjectMapper.readSession(content)
        val map = kafkaObjectMapper.readPathVariable(content, "userId")
        return tryToResponse {
            userService.removeUser(map)
            SUCCESS
        }
    }

    @KafkaListener(topics = ["user-update-request"])
    fun `user-update-message`(content: String): String {
        imperativeSessionStorage.userId = kafkaObjectMapper.readSession(content)
        val map = kafkaObjectMapper.readPathVariable(content, "userId")
        val body = kafkaObjectMapper.readBody(content, UserEditProjection::class.java)
        return tryToResponse {
            userService.editUser(map, body)
            SUCCESS
        }
    }

    @KafkaListener(topics = ["profile-getall-request"])
    fun `profile-getall-request`(content: String): String {
        imperativeSessionStorage.userId = kafkaObjectMapper.readSession(content)
        return tryToResponse {
            kafkaObjectMapper.convertToMessageFromBodyObject(
                profileService.fetchAllProfiles()
            )
        }
    }

    @KafkaListener(topics = ["profile-get-request"])
    fun `profile-get-request`(content: String): String {
        imperativeSessionStorage.userId = kafkaObjectMapper.readSession(content)
        val map = kafkaObjectMapper.readPathVariable(content, "profileId")
        return tryToResponse {
            kafkaObjectMapper.convertToMessageFromBodyObject(
                profileService.fetchUserProfile(map)
            )
        }
    }

    @KafkaListener(topics = ["user-get-request"])
    fun `user-get-request`(content: String): String {
        imperativeSessionStorage.userId = kafkaObjectMapper.readSession(content)
        val map = kafkaObjectMapper.readPathVariable(content, "userId")
        return tryToResponse {
            kafkaObjectMapper.convertToMessageFromBodyObject(
                userService.getUser(map)
            )
        }
    }

    @KafkaListener(topics = ["user-getall-request"])
    fun `user-getall-request`(content: String): String {
        imperativeSessionStorage.userId = kafkaObjectMapper.readSession(content)
        return tryToResponse {
            kafkaObjectMapper.convertToMessageFromBodyObject(
                userService.getAllUsers()
            )
        }
    }
}

