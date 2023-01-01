package com.example.entity

import com.example.ProfileEditProjection
import com.example.event.*
import org.example.DDDEntity
import org.example.ifBlank
import java.time.LocalDate
import java.util.*

class Profile(
    val profileID: UUID,
    var username: String,
    var birthday: LocalDate?,
    val userId: UUID,
    val groups: MutableSet<UUID> = mutableSetOf(),
    val followed: MutableSet<UUID> = mutableSetOf(),
    var avatar: String,
): DDDEntity() {
    override val entityId: UUID?
        get() = profileID

    fun addGroup(group: UUID): ProfileAddedToGroup {
        groups.add(group)
        return ProfileAddedToGroup(this)
    }

    fun removeGroup(group: UUID): ProfileRemovedFromGroup {
        groups.remove(group)
        return ProfileRemovedFromGroup(this)
    }

    fun addFollowedProfile(profileID: UUID): ProfileToFollowAdded {
        followed.add(profileID)
        return ProfileToFollowAdded(this)
    }

    fun removeFollowedProfile(profile: UUID): ProfileToFollowRemoved {
        followed.remove(profile)
        return ProfileToFollowRemoved(this)
    }

    fun edit(profile: ProfileEditProjection): ProfileEdited {
        avatar = profile.avatar ifBlank avatar
        birthday = profile.birthday ?: birthday
        username = profile.username ifBlank (username ?: "")
        return ProfileEdited(this)
    }
}