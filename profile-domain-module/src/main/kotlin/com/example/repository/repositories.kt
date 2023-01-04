package com.example.repository

import com.example.entity.Profile
import com.example.entity.User
import org.example.EntityRepository
import java.util.*

interface UserRepository: EntityRepository<User> {
    fun existsByEmail(email: String): Boolean

    fun findAll(): List<User>
}

interface ProfileRepository: EntityRepository<Profile> {
}