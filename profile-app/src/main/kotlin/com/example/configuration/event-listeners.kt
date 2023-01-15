package com.example.configuration

import com.example.service.ProfileService
import com.example.servicechassis.ImperativeSessionStorage
import com.example.servicechassis.toUUID
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.kafka.annotation.KafkaListener


class EventListeners(val profileService: ProfileService, val objectMapper: ObjectMapper, val imperativeSessionStorage: ImperativeSessionStorage) {

    @KafkaListener(topics = ["profile-removed-event"])
    fun `profile-removed-event`(event: String) {

        val group = (objectMapper.readTree(event)["group"].toString().replace("\"", "")).toUUID()
        val profile = (objectMapper.readTree(event)["profile"].toString().replace("\"", "")).toUUID()
        imperativeSessionStorage.userId = profile
        profileService.removeFromGroup(profile, group)
    }

    @KafkaListener(topics = ["profile-added-event"])
    fun `profile-added-event`(event: String) {
        val group = (objectMapper.readTree(event)["group"].toString().replace("\"", "")).toUUID()
        val profile = (objectMapper.readTree(event)["profile"].toString().replace("\"", "")).toUUID()
        imperativeSessionStorage.userId = profile
        profileService.addToGroup(profile, group)
    }
}