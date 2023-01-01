package com.example.repository

import com.example.entity.User
import com.example.repository.table.ProfileTable
import com.example.repository.table.UserTable
import org.springframework.data.mongodb.repository.MongoRepository
import java.util.*


interface UserMongoRepository: MongoRepository<UserTable, UUID> {
    fun existsByEmail(email: String): Boolean
}

interface ProfileMongoRepository: MongoRepository<ProfileTable, UUID> {
}
