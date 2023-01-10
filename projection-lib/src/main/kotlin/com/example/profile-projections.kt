package com.example

import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDate
import java.util.*

data class CreateUserProjection(
    val username: String?,
    @DateTimeFormat(pattern="yyyy-MM-dd") val birthday: LocalDate?,
    var email: String?,
    var password: String?
)

data class UserEditProjection(
    var email: String,
)

data class ProfileEditProjection(
    var username: String?,
    @DateTimeFormat(pattern="yyyy-MM-dd") var birthday: LocalDate?,
    var avatar: String?,
)

data class ProfileProjection(
    val profileID: UUID,
    var username: String,
    @DateTimeFormat(pattern="yyyy-MM-dd") var birthday: LocalDate?,
    val userId: UUID,
    val groups: MutableSet<UUID> = mutableSetOf(),
    val followed: MutableSet<UUID> = mutableSetOf(),
)

data class ProfileProjectionWithFollow(
    val profileID: UUID
)