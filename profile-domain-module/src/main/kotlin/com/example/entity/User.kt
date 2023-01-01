package com.example.entity

import EmailChangedEvent
import org.example.DDDEntity
import java.util.UUID

class User(
        val userId: UUID,
        var email: String,
): DDDEntity() {
        override val entityId: UUID?
                get() = userId

        fun editEmail(email: String): EmailChangedEvent {
                this.email = email
                return EmailChangedEvent(email)
        }
}