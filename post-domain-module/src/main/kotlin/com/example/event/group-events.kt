package com.example.event

import com.example.entity.Group
import org.example.DomainEvent
import java.util.UUID

abstract class GroupEvent: DomainEvent<Group>()

class GroupCreated(val group: Group): GroupEvent()

class ProfileAddedEvent(group: UUID, profile: UUID): GroupEvent()

class RemoveProfileEvent(group: UUID, profile: UUID): GroupEvent()
class RemoveProfilesEvent(group: UUID, profiles: Set<UUID>): GroupEvent()

class AdministratorAddedEvent(val group: Group): GroupEvent()

class AdministratorRemovedEvent(val group: Group): GroupEvent()

class GroupRemovedEvent(val group: UUID): GroupEvent()