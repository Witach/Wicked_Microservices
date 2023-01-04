package org.example

import java.sql.Timestamp
import java.util.Objects
import java.util.UUID

interface AuthServerClient {

    fun addUser(createUserDto: CreateUserDto)

    fun fetchUserByUsername(username: String): UserFetched?

    fun persistUser(createUserDto: CreateUserDto): UserFetched?

    fun updateUser(user: UserFetched): UserFetched?

    fun fetchUserByUserId(id: UUID): UserFetched?

    fun deleteUserById(id: UUID)

}

data class CreateUserDto(
    val firstName: String,
    val lastName: String,
    val email: String,
    val enabled: Boolean,
    val username: String,
    val credentials: Set<CredentialDto>
)

data class CredentialDto(
    val type: String = "password",
    val value: String,
    val temporary: Boolean = false
)


data class UserFetched(
    val id : String? = null,
    val createdTimestamp: String? = null,
    val username: String? = null,
    val enabled: String? = null,
    val emailVerified: String? = null,
    val firstName: String? = null,
    val lastName: String? = null,
    val email: String? = null,
    val notBefore: String? = null,
)