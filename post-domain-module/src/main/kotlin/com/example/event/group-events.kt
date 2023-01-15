package com.example.event

import com.example.entity.Group
import org.example.DomainEvent
import java.util.*

abstract class GroupEvent: DomainEvent<Group>()

class GroupCreated(val group: Group): GroupEvent()

class ProfileAddedEvent(val group: UUID, val profile: UUID): GroupEvent()

class RemoveProfileEvent(val group: UUID, val profile: UUID): GroupEvent()
class RemoveProfilesEvent(val group: UUID, val profiles: Set<UUID>): GroupEvent()

class AdministratorAddedEvent(val group: Group): GroupEvent()

class AdministratorRemovedEvent(val group: Group): GroupEvent()

class GroupRemovedEvent(val group: UUID): GroupEvent()