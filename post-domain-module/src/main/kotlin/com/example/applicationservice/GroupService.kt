package com.example.applicationservice

import com.example.GroupCreateProjection
import com.example.entity.Group
import com.example.event.GroupCreated
import com.example.event.GroupRemovedEvent
import com.example.event.ProfileAddedEvent
import com.example.event.RemoveProfileEvent
import com.example.repository.*
import org.example.EntityNotFoundException
import org.example.EventPublisher
import java.util.*

class GroupService(
    private val groupRepository: GroupRepository,
    private val profileService: ProfileService,
    private val eventPublisher: EventPublisher
) {

    fun addProfile(profile: UUID, group: UUID) {
        groupRepository.findById(group)?.also {
            it.addProfile(profile)
            val targetGroup =  groupRepository.save(it)
            eventPublisher.publish(ProfileAddedEvent(targetGroup.entityId!!, profile))
            profileService.addGroupToProfile(group, profile)
        } ?: throw EntityNotFoundException(Group::class.java, group)
    }

    fun removeProfile(profile: UUID, group: UUID) {
        groupRepository.findById(group)?.also {
            it.removeProfile(profile)
            groupRepository.save(it)
            eventPublisher.publish(RemoveProfileEvent(it.entityId!!, profile))
            profileService.removeGroupFromProfile(group, profile)
        } ?: throw EntityNotFoundException(Group::class.java, group)
    }

    fun addGroup(group: GroupCreateProjection) {
        val createdGroup = groupRepository.save(Group(
            groupInt = group.groupInt,
            name = group.name,
            owner = group.owner,
            description = group.description,
            image = group.image
        ))
        eventPublisher.publish(GroupCreated(createdGroup))
    }

    fun removeGroup(group: UUID) {
        groupRepository.findById(group)?.also {
            if(it.isAdministrator(group)) {
                profileService.removeGroupFromProfiles(group, it.profiles)
                groupRepository.deleteById(group)
                eventPublisher.publish(GroupRemovedEvent(group))
            }
        } ?: throw EntityNotFoundException(Group::class.java, group)
    }

    fun existsById(group: UUID): Boolean {
        return groupRepository.existById(group)
    }

}