package com.example.repository

import com.example.entity.User
import com.example.repository.table.UserTable
import org.springframework.stereotype.Component
import java.util.*

@Component
class UserRepositoryImpl(val mongoRepository: UserMongoRepository): UserRepository {
    override fun save(e: User): User {
        return mongoRepository.save(e.toUserTable()).toUser()
    }

    override fun deleteById(id: UUID) {
        mongoRepository.deleteById(id)
    }

    override fun removeAll(ids: List<UUID>) {
        mongoRepository.deleteAllById(ids)
    }

    override fun findAll(): List<User> {
        return mongoRepository.findAll()
            .map { it.toUser() }
    }

    override fun findById(id: UUID): User? {
        return mongoRepository.findById(id)
            .map { it.toUser() }
            .orElse(null)
    }

    override fun findAllByIds(ids: List<UUID>): List<User> {
        return mongoRepository.findAllById(ids)
            .map { it.toUser() }
    }

    override fun existById(uuid: UUID): Boolean {
        return mongoRepository.existsById(uuid)
    }

    override fun existsByEmail(email: String): Boolean {
        return mongoRepository.existsByEmail(email)
    }
}

fun User.toUserTable(): UserTable = UserTable(userId, email)

fun UserTable.toUser(): User = User(userId, email)