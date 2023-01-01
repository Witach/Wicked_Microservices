package com.example.service

import com.example.ProfileEditProjection
import com.example.applicationservice.SessionStorage
import com.example.entity.Profile
import com.example.repository.ProfileRepository
import org.example.EntityNotFoundException
import org.example.EventPublisher
import java.util.*

class ProfileService(
    private val profileRepository: ProfileRepository,
    private val groupServiceClient: GroupServiceClient,
    private val sessionStorage: SessionStorage,
    private val eventPublisher: EventPublisher
) {
    fun editProfile(profileId: UUID, profile: ProfileEditProjection) {
        profileRepository.findById(profileId)?.also {
            val event = it.edit(profile)
            profileRepository.save(it)
            eventPublisher.publish(event)
        } ?: throw EntityNotFoundException(Profile::class.java, profileId)
    }

    fun addToGroup(profileId: UUID, groupUUID: UUID) {

        throwIfNoSuchGroupd(groupUUID)

        profileRepository.findById(profileId)?.also {
            sessionStorage.throwIfNoPermission(it.userId)
            val event = it.addGroup(groupUUID)
            profileRepository.save(it)
            eventPublisher.publish(event)
        }
    }

    fun removeFromGroup(profileId: UUID, groupUUID: UUID) {

        throwIfNoSuchGroupd(groupUUID)

        profileRepository.findById(profileId)?.also {
            sessionStorage.throwIfNoPermission(it.userId)
            val event = it.removeGroup(groupUUID)
            profileRepository.save(it)
            eventPublisher.publish(event)
        }
    }

    fun startToFollow(profileId: UUID, profileToFollow: UUID) {
        if(!profileRepository.existById(profileToFollow)) {
            throw EntityNotFoundException(Profile::class.java, profileToFollow)
        }
        profileRepository.findById(profileId)?.also {
            val event = it.addFollowedProfile(profileToFollow)
            profileRepository.save(it)
            eventPublisher.publish(event)
        } ?: throw EntityNotFoundException(Profile::class.java, profileId)
    }

    fun removeFollowedProfile(profile: UUID, profileToFollow: UUID) {
        if(!profileRepository.existById(profileToFollow)) {
            throw EntityNotFoundException(Profile::class.java, profileToFollow)
        }

        profileRepository.findById(profile)?.also {
            val event = it.removeFollowedProfile(profileToFollow)
            profileRepository.save(it)
            eventPublisher.publish(event)
        } ?: throw EntityNotFoundException(Profile::class.java, profile)
    }

    fun throwIfNoSuchGroupd(groupUUID: UUID) {
        if(!groupServiceClient.existsById(groupUUID)) {
            throw GroupDeosNotExists(groupUUID);
        }
    }

    class GroupDeosNotExists(groupUUID: UUID) : RuntimeException("Group of id=$groupUUID does not exits")
}