package com.example.repository

import com.example.entity.Profile
import com.example.entity.User
import org.example.EntityRepository

interface UserRepository: EntityRepository<User> {
    fun existsByEmail(email: String): Boolean
}

interface ProfileRepository: EntityRepository<Profile> {
}