package com.example.service

import com.example.ProfileEditProjection
import com.example.ProfileProjectionWithFollow
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
            eventPublisher.publish(event, "profile-edited-event")
        } ?: throw EntityNotFoundException(Profile::class.java, profileId)
    }

    fun addToGroup(profileId: UUID, groupUUID: UUID) {

        throwIfNoSuchGroupd(groupUUID)

        profileRepository.findById(profileId)?.also {
            sessionStorage.throwIfNoPermission(it.userId)
            val event = it.addGroup(groupUUID)
            profileRepository.save(it)
            eventPublisher.publish(event, "profile-added-to-group")
        }
    }

    fun removeFromGroup(profileId: UUID, groupUUID: UUID) {

        throwIfNoSuchGroupd(groupUUID)

        profileRepository.findById(profileId)?.also {
            sessionStorage.throwIfNoPermission(it.userId)
            val event = it.removeGroup(groupUUID)
            profileRepository.save(it)
            eventPublisher.publish(event, "profile-removed-from-group")
        }
    }

    fun startToFollow(profileId: UUID, profileToFollow: ProfileProjectionWithFollow) {
        if(!profileRepository.existById(profileToFollow.profileID)) {
            throw EntityNotFoundException(Profile::class.java, profileToFollow.profileID)
        }
        profileRepository.findById(profileId)?.also {
            val event = it.addFollowedProfile(profileToFollow.profileID)
            profileRepository.save(it)
            eventPublisher.publish(event, "profile-started-to-follow")
        } ?: throw EntityNotFoundException(Profile::class.java, profileId)
    }

    fun removeFollowedProfile(profile: UUID, profileToFollow: UUID) {
        if(!profileRepository.existById(profileToFollow)) {
            throw EntityNotFoundException(Profile::class.java, profileToFollow)
        }

        profileRepository.findById(profile)?.also {
            val event = it.removeFollowedProfile(profileToFollow)
            profileRepository.save(it)
            eventPublisher.publish(event, "profile-removed-followed-profile")
        } ?: throw EntityNotFoundException(Profile::class.java, profile)
    }

    fun fetchUserProfile(profileId: UUID): Profile {
        return profileRepository.findById(profileId) ?: throw EntityNotFoundException(Profile::class.java, profileId)
    }

    fun fetchAllProfiles(): Set<Profile> {
        return profileRepository.findAll()
    }

    fun throwIfNoSuchGroupd(groupUUID: UUID) {
        if(!groupServiceClient.existsById(groupUUID)) {
            throw GroupDeosNotExists(groupUUID);
        }
    }

    class GroupDeosNotExists(groupUUID: UUID) : RuntimeException("Group of id=$groupUUID does not exits")
}