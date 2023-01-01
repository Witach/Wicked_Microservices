package com.example.event

import com.example.entity.Profile
import org.example.DomainEvent

abstract class ProfileEvent: DomainEvent<Profile>()

class ProfileAddedToGroup(val profile: Profile): ProfileEvent()

class ProfileRemovedFromGroup(val profile: Profile): ProfileEvent()

class ProfileToFollowAdded(val profile: Profile): ProfileEvent()

class ProfileToFollowRemoved(val profile: Profile): ProfileEvent()

class ProfileEdited(val profile: Profile): ProfileEvent()
