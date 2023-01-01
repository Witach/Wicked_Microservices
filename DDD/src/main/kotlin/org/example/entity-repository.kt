package org.example

import java.util.*

interface EntityRepository <E: DDDEntity> {
    fun save(e: E): E
    fun deleteById(id: UUID)
    fun removeAll(ids: List<UUID>)

    fun findById(id: UUID): E?
    fun findAllByIds(ids: List<UUID>): List<E>
    fun existById(uuid: UUID): Boolean
}
