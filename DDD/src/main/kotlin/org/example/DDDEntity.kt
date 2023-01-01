package org.example

import java.util.UUID

abstract class DDDEntity {

    abstract val entityId: UUID?
        get

    override operator fun equals(other: Any?): Boolean {
        return when(other) {
            (this === other) -> true
            is DDDEntity -> entityId == other.entityId
            else -> false
        }
    }

    override fun toString(): String {
        return javaClass.simpleName + entityId
    }
}