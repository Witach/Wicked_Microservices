package com.example.entity

import com.example.GroupProjection
import com.example.event.AdministratorAddedEvent
import com.example.event.AdministratorRemovedEvent
import com.example.event.ProfileAddedEvent
import com.example.event.RemoveProfileEvent
import org.example.DDDEntity
import java.util.UUID

class Group(
    val groupInt: UUID,
    val name: String,
    val owner: UUID,
    val description: String,
    val image: String,
    val profiles: MutableSet<UUID> = mutableSetOf(),
    val administrators: MutableSet<UUID> = mutableSetOf()
): DDDEntity() {

    override val entityId: UUID?
        get() = groupInt

    public fun addProfile(profile: UUID): ProfileAddedEvent {
        profiles.add(profile)
        return ProfileAddedEvent(groupInt, profile)
    }

    public fun removeProfile(profile: UUID): RemoveProfileEvent {
        profiles.remove(profile);
        return RemoveProfileEvent(groupInt, profile)
    }

    public fun addAdministrator(admin: UUID): AdministratorAddedEvent {
        if(!containsProfile(admin)) {
            throw ProfileNotIncludedException("Member of id=$admin is not part of the group")
        }
        administrators.add(admin)
        return AdministratorAddedEvent(this)
    }

    public fun removeAdministrator(admin: UUID): AdministratorRemovedEvent {
        administrators.remove(admin)
        return AdministratorRemovedEvent(this)
    }

    public fun isAdministrator(user: UUID): Boolean {
        return administrators.contains(user) || owner == user
    }

    public fun containsProfile(profile: UUID): Boolean {
        return profiles.contains(profile) || owner == profile
    }

    fun toProjection() = GroupProjection(
        groupInt = groupInt,
        name = name,
        description = description,
        image = image,
        owner = owner,
        profiles = profiles.toList(),
    )
}