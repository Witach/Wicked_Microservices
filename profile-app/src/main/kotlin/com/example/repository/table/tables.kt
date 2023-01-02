package com.example.repository.table

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.DocumentReference
import java.time.LocalDate
import java.util.*

@Document("profiles")
data class ProfileTable(
    @Id val profileID: UUID,
    @DocumentReference val user:UserTable,
    var username: String?,
    var birthday: LocalDate?,
    val groups: MutableSet<UUID> = mutableSetOf(),
    val followed: MutableSet<UUID> = mutableSetOf(),
)

@Document("users")
data class UserTable(
    @Id val userId: UUID,
    var email: String,
)