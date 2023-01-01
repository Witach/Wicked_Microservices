package com.example.repository

import com.example.entity.Profile
import com.example.repository.table.ProfileTable
import com.example.repository.table.UserTable
import org.springframework.stereotype.Component
import java.util.*

@Component
class ProfileRepositoryImpl(val profileMongoRepository: ProfileMongoRepository): ProfileRepository {
    override fun save(e: Profile): Profile {
        return profileMongoRepository.save(e.toProfileTable()).toProfile()
    }

    override fun deleteById(id: UUID) {
        profileMongoRepository.deleteById(id)
    }

    override fun removeAll(ids: List<UUID>) {
        profileMongoRepository.deleteAllById(ids)
    }

    override fun findById(id: UUID): Profile? {
        return profileMongoRepository.findById(id)
            .map { it.toProfile() }
            .orElse(null)
    }

    override fun findAllByIds(ids: List<UUID>): List<Profile> {
        return profileMongoRepository.findAllById(ids)
            .map { it.toProfile() }
    }

    override fun existById(uuid: UUID): Boolean {
        return profileMongoRepository.existsById(uuid)
    }
}

fun Profile.toProfileTable(): ProfileTable = ProfileTable(
    profileID, UserTable(userId, this.username),  username, birthday, groups, followed, avatar
)

fun ProfileTable.toProfile(): Profile = Profile(
    profileID, user.email, birthday, user.userId, groups, followed, avatar
)
