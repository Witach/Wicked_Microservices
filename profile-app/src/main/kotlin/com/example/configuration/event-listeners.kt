package com.example.configuration

import com.example.service.ProfileService
import com.example.servicechassis.ImperativeSessionStorage
import com.example.servicechassis.toUUID
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.kafka.annotation.KafkaListener
import java.util.*


open class EventListeners(val profileService: ProfileService, val objectMapper: ObjectMapper, val imperativeSessionStorage: ImperativeSessionStorage) {

    @KafkaListener(topics = ["profile-removed-event"])
    fun `profile-removed-event`(event: String) {
        val group = readGroup(event)
        val profile = readProfile(event)
        imperativeSessionStorage.userId = profile
        profileService.removeFromGroup(profile, group)
    }

    @KafkaListener(topics = ["profile-added-event"])
    fun `profile-added-event`(event: String) {
        val group = readGroup(event)
        val profile = readProfile(event)
        imperativeSessionStorage.userId = profile
        profileService.addToGroup(profile, group)
    }

    fun readGroup(event: String): UUID {
        return (objectMapper.readTree(event)["group"].toString().replace("\"", "")).toUUID()
    }

    fun readProfile(event: String): UUID {
        return (objectMapper.readTree(event)["profile"].toString().replace("\"", "")).toUUID()
    }
}