package com.example.applicationservice

import java.util.UUID

interface ProfileService {
    fun addGroupToProfile(group: UUID, profile: UUID)
    fun removeGroupFromProfile(group: UUID, profile: UUID)
    fun removeGroupFromProfiles(group: UUID, profile: Set<UUID>)
}
