package com.example.applicationservice

import com.example.GroupCreateProjection
import com.example.GroupId
import com.example.GroupProjection
import com.example.ProfileProjectionWithFollow
import com.example.entity.Group
import com.example.event.GroupCreated
import com.example.event.GroupRemovedEvent
import com.example.event.ProfileAddedEvent
import com.example.event.RemoveProfileEvent
import com.example.repository.*
import org.example.EntityNotFoundException
import org.example.EventPublisher
import org.example.RequiredParamsNotIncludedException
import java.util.*
import java.util.function.Predicate

class GroupService(
    private val groupRepository: GroupRepository,
    private val profileService: ProfileService,
    private val eventPublisher: EventPublisher,
    private val administratorPolicy: Predicate<Group>,
    private val groupPostService: GroupPostService,
) {

    fun addProfile(profile: ProfileProjectionWithFollow, group: UUID) {
        groupRepository.findById(group)?.also {
            it.addProfile(profile.profileID)
            val targetGroup =  groupRepository.save(it)
            eventPublisher.publish(ProfileAddedEvent(targetGroup.entityId!!, profile.profileID), "profile-added-event")
            profileService.addGroupToProfile(group, profile.profileID)
        } ?: throw EntityNotFoundException(Group::class.java, group)
    }

    fun removeProfile(profile: UUID, group: UUID) {
        groupRepository.findById(group)?.also {
            it.removeProfile(profile)
            groupRepository.save(it)
            eventPublisher.publish(RemoveProfileEvent(it.entityId!!, profile), "profile-removed-event")
            profileService.removeGroupFromProfile(group, profile)
        } ?: throw EntityNotFoundException(Group::class.java, group)
    }

    fun addGroup(group: GroupCreateProjection) {
        val createdGroup = groupRepository.save(Group(
            groupInt = UUID.randomUUID(),
            name = group.name ?: "",
            owner = group.owner!!,
            description = group.description ?: "",
            image = group.image ?: "",
            administrators = mutableSetOf(group.owner!!)
        ))
        eventPublisher.publish(GroupCreated(createdGroup), "group-created-event")
    }

    fun removeGroup(group: UUID) {
        groupRepository.findById(group)?.also {
            if(administratorPolicy.test(it)) {
                profileService.removeGroupFromProfiles(group, it.profiles)
                groupRepository.deleteById(group)
                eventPublisher.publish(GroupRemovedEvent(group), "group-removed-event")
            }
        } ?: throw EntityNotFoundException(Group::class.java, group)
    }

    fun existsById(group: UUID): Boolean {
        return groupRepository.existById(group)
    }

    fun fetchGroup(group: GroupId): GroupProjection {
        val groupVal = groupRepository.findById(group.groupId) ?: throw EntityNotFoundException(Group::class.java, group.groupId)
        val posts = groupPostService.getGroupPosts(groupVal.groupInt)
        return groupVal.toProjection(posts)
    }

}