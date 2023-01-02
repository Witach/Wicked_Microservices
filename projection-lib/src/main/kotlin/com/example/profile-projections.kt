package com.example

import java.time.LocalDate
import java.util.*

data class CreateUserProjection(
    val username: String?,
    val birthday: LocalDate?,
    var email: String?,
    var passowrd: String?
)

data class UserEditProjection(
    var email: String,
)

data class ProfileEditProjection(
    var username: String?,
    var birthday: LocalDate?,
    var avatar: String?,
)

data class ProfileProjection(
    val profileID: UUID,
    var username: String?,
    var birthday: LocalDate?,
    val userId: UUID,
    var avatar: String,
)