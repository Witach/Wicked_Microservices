package com.example.repository

import com.example.entity.Group
import com.example.repository.GroupTable
import org.springframework.stereotype.Component
import java.util.*

@Component
class GroupRepositoryImpl(private val groupJpaRepository: GroupJpaRepository): GroupRepository {

    override fun save(e: Group): Group {
        return groupJpaRepository.save(e.toGroupTable()).toGroup()
    }

    override fun deleteById(id: UUID) {
        groupJpaRepository.deleteById(id)
    }

    override fun removeAll(ids: List<UUID>) {
        groupJpaRepository.deleteAllById(ids)
    }

    override fun findById(id: UUID): Group? {
        return groupJpaRepository.findById(id)
            .map { it.toGroup() }
            .orElse(null)
    }

    override fun findAllByIds(ids: List<UUID>): List<Group> {
        return groupJpaRepository.findAllById(ids)
            .map { it.toGroup() }
    }

    override fun existById(uuid: UUID): Boolean {
        return groupJpaRepository.existsById(uuid)
    }
}

fun Group.toGroupTable(): GroupTable {
    return GroupTable(groupInt, name, owner, description, image, profiles, administrators)
}

fun GroupTable.toGroup(): Group {
    return Group(groupInt, name, owner, description, image, profiles.toMutableSet(), administrators.toMutableSet())
}