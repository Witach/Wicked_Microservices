package com.example.service

import com.example.GroupId
import com.example.RemoveGroupAssociations
import com.example.applicationservice.ProfileService
import com.example.entity.Group
import com.example.event.ProfileAddedEvent
import com.example.event.RemoveProfileEvent
import com.example.event.RemoveProfilesEvent
import com.fasterxml.jackson.databind.ObjectMapper
import org.example.DomainEvent
import org.springframework.context.annotation.Profile
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component
import java.util.*

@Component
@Profile("feign")
class ProfileServiceClientFeign(val profileServiceFeignClient: ProfileServiceFeignClient): ProfileService {
    override fun addGroupToProfile(group: UUID, profile: UUID) {
        profileServiceFeignClient.addGroupToProfile(profile, group)
    }

    override fun removeGroupFromProfile(group: UUID, profile: UUID) {
        profileServiceFeignClient.removeGroupFromProfile(profile, group)
    }

    override fun removeGroupFromProfiles(group: UUID, profile: Set<UUID>) {
        profileServiceFeignClient.removeGroupFromProfiles(RemoveGroupAssociations(group, profile))
    }
}

@Component
@Profile("kafka")
class ProfileServiceClientKafka(val kafkaTemplate: KafkaTemplate<String, String>, val objectMapper: ObjectMapper): ProfileService {
    override fun addGroupToProfile(group: UUID, profile: UUID) {
        objectMapper.writeValueAsString(ProfileAddedEvent(group, profile)).let {
            kafkaTemplate.send("group-profileadded-event", it)
        }
    }

    override fun removeGroupFromProfile(group: UUID, profile: UUID) {
        objectMapper.writeValueAsString(RemoveProfileEvent(group, profile)).let {
            kafkaTemplate.send("group-profileremoved-event", it)
        }
    }

    override fun removeGroupFromProfiles(group: UUID, profile: Set<UUID>) {
        objectMapper.writeValueAsString(RemoveProfilesEvent(group, profile)).let {
            kafkaTemplate.send("group-profileremoved-event", it)
        }
    }
}

@Profile("dumb")
@Component
class ProfileServiceDumb(val profileServiceFeignClient: ProfileServiceFeignClient): ProfileService {
    override fun addGroupToProfile(group: UUID, profile: UUID) {
    }

    override fun removeGroupFromProfile(group: UUID, profile: UUID) {
    }

    override fun removeGroupFromProfiles(group: UUID, profile: Set<UUID>) {
    }
}