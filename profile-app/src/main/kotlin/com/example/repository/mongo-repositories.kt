package com.example.repository

import com.example.entity.User
import com.example.repository.table.ProfileTable
import com.example.repository.table.UserTable
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository
import java.util.*


@Repository
interface UserMongoRepository: MongoRepository<UserTable, UUID> {
    fun existsByEmail(email: String): Boolean
}

interface ProfileMongoRepository: MongoRepository<ProfileTable, UUID> {
}
