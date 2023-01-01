package org.example

import java.util.UUID

abstract class DomainEvent<T: DDDEntity> {
    private val _eventId = UUID.randomUUID()
    val eventId: UUID
        get() = _eventId
}