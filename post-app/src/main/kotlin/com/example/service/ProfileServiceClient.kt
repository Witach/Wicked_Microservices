package com.example.service

import com.example.RemoveGroupAssociations
import com.example.applicationservice.ProfileService
import com.example.applicationservice.SessionStorage
import com.example.event.ProfileAddedEvent
import com.example.event.RemoveProfileEvent
import com.example.event.RemoveProfilesEvent
import com.examples.lib.ProfileServiceGrpc.ProfileServiceBlockingStub
import com.fasterxml.jackson.databind.ObjectMapper
import net.devh.boot.grpc.client.inject.GrpcClient
import org.springframework.context.annotation.Profile
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service
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

@Profile("grpc")
@Service
class ProfileServiceGRPC(val sessionStorage: SessionStorage): ProfileService {

    @GrpcClient("profileService") val profileServiceClient: ProfileServiceBlockingStub? = null
    override fun addGroupToProfile(group: UUID, profile: UUID) {
        profileServiceClient!!.addGroupToProfile(
            com.examples.lib.AddGroupToProfileMessage.newBuilder()
                .setSession(sessionStorage.sessionOwner.userId.toString())
                .setGroupToAdd(group.toString())
                .setProfileId(profile.toString())
                .build()
        )
    }

    override fun removeGroupFromProfile(group: UUID, profile: UUID) {
        profileServiceClient!!.removeGroupFromProfile(
            com.examples.lib.RemoveGroupFromProfileMessage.newBuilder()
                .setSession(sessionStorage.sessionOwner.userId.toString())
                .setGroupId(group.toString())
                .setProfileId(profile.toString())
                .build()
        )
    }

    override fun removeGroupFromProfiles(group: UUID, profile: Set<UUID>) {
        profileServiceClient!!.removeGroupFromProfiles(
            com.examples.lib.RemoveGroupFromProfileMessages.newBuilder()
                .setSessionId(sessionStorage.sessionOwner.userId.toString())
                .setGroupId(group.toString())
                .addAllProfiles(profile.map { it.toString() })
                .build()
        )
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