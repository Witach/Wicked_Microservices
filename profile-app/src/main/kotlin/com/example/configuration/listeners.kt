package com.example.configuration

import com.example.CreateUserProjection
import com.example.ProfileEditProjection
import com.example.UserEditProjection
import com.example.service.ProfileService
import com.example.service.UserService
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service
import java.util.*

@Service
class Listeners(val kafkaTemplate: KafkaTemplate<String, String>,
                val objectMapper: ObjectMapper,
                val profileService: ProfileService,
                val userService: UserService) {

    @KafkaListener(topics = ["profile-update-message"], groupId = "profile-update-message-consumer")
    fun `profile-update-message`(content: String) {
        val map = objectMapper.readValue(content, Map::class.java)
        profileService.editProfile(UUID.fromString(map["profileId"] as String),
            objectMapper.readValue(map["profile"] as String, ProfileEditProjection::class.java))
    }

    @KafkaListener(topics = ["profile-addtogroup-message"], groupId = "profile-addtogroup-message-consumer")
    fun `profile-addtogroup-message`(content: String) {
        val map = objectMapper.readValue(content, Map::class.java)
        profileService.addToGroup(UUID.fromString(map["profileId"] as String),
            UUID.fromString(map["groupId"] as String))
    }

    @KafkaListener(topics = ["profile-removefromgroup-message"], groupId = "profile-removefromgroup-message-consumer")
    fun `profile-removefromgroup-message`(content: String) {
        val map = objectMapper.readValue(content, Map::class.java)
        profileService.removeFromGroup(UUID.fromString(map["profileId"] as String),
            UUID.fromString(map["groupId"] as String))
    }

    @KafkaListener(topics = ["profile-starttofollow-message"], groupId = "profile-starttofollow-message-consumer")
    fun `profile-starttofollow-message`(content: String) {
        val map = objectMapper.readValue(content, Map::class.java)
        profileService.startToFollow(UUID.fromString(map["profileId"] as String),
            UUID.fromString(map["profileToFollow"] as String))
    }

    @KafkaListener(topics = ["profile-stoptofollow-message"], groupId = "profile-stoptofollow-message-consumer")
    fun `profile-stoptofollow-message`(content: String) {
        val map = objectMapper.readValue(content, Map::class.java)
        profileService.removeFollowedProfile(UUID.fromString(map["profileId"] as String),
            UUID.fromString(map["profileToFollow"] as String))
    }

    @KafkaListener(topics = ["user-creat-message"], groupId = "user-creat-message-consumer")
    fun `user-creat-message`(content: String) {
        val map = objectMapper.readValue(content, Map::class.java)
        userService.registerNewUser(objectMapper.readValue(map["user"] as String, CreateUserProjection::class.java))
    }

    @KafkaListener(topics = ["user-delete-message"], groupId = "user-delete-message-consumer")
    fun `user-delete-message`(content: String) {
        val map = objectMapper.readValue(content, Map::class.java)
        userService.removeUser(UUID.fromString(map["userId"] as String))
    }

    @KafkaListener(topics = ["user-update-message"], groupId = "user-update-message-consumer")
    fun `user-update-message`(content: String) {
        val map = objectMapper.readValue(content, Map::class.java)
        userService.editUser(UUID.fromString(map["userId"] as String),
            objectMapper.readValue(map["user"] as String, UserEditProjection::class.java))
    }

}
