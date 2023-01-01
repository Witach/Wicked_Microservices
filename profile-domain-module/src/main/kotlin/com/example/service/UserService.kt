package com.example.service

import EmailChangedEvent
import PasswoordChangedEvent
import UserCreatedEvent
import UserDeletedEvent
import com.example.CreateUserProjection
import com.example.UserEditProjection
import com.example.applicationservice.SessionStorage
import com.example.entity.Profile
import com.example.entity.User
import com.example.repository.ProfileRepository
import com.example.repository.UserRepository
import org.example.*
import java.lang.RuntimeException
import java.util.*

class UserService(
    private val userRepository: UserRepository,
    private val profileRepository: ProfileRepository,
    private val sessionStorage: SessionStorage,
    private val eventPublisher: EventPublisher,
    private val authClient: AuthServerClient
) {
    fun registerNewUser(createUserProjection: CreateUserProjection) {
        if(!userRepository.existsByEmail(createUserProjection.email as String)) {
           throw EmailInUseException("Provided email is already in use")
        }

        val persistedUser = authClient.persistUser(createUserProjection.toCreateDTO())

        persistedUser ?: throw RegistrationException("x")

        val user = userRepository.save(User(
            userId = persistedUser.id!!,
            email = createUserProjection.email!!
        ))

        val profile = profileRepository.save(Profile(
            profileID = persistedUser.id!!,
            username = createUserProjection.username!!,
            birthday = createUserProjection.birthday,
            userId = user.userId,
            avatar = createUserProjection.avatar ?: "",
        ))

        eventPublisher.publish(UserCreatedEvent(user, profile))
    }

    fun editUser(user: UUID, userPost: UserEditProjection) {
        val updatedUser = authClient.updateUser(userPost.toUserFetched(user))

        updatedUser ?: throw RegistrationException("x")

        userRepository.findById(user)?.also {
            it.editEmail(userPost.email)
            userRepository.save(it)
            eventPublisher.publish(EmailChangedEvent(userPost.email))
        } ?: throw EntityNotFoundException(User::class.java, user)
    }

    fun removeUser(user: UUID) {
        sessionStorage.throwIfNoPermission(user)

        authClient.deleteUserById(user)

        if(userRepository.existById(user)) {
            throw EntityNotFoundException(User::class.java, user)
        }
        userRepository.deleteById(user)
        eventPublisher.publish(UserDeletedEvent(user))
    }

    class EmailInUseException(message: String): RuntimeException(message)
}

fun CreateUserProjection.toCreateDTO() = CreateUserDto(
    this.email!!,
    this.username!!,
    this.email!!,
    false,
    this.username!!,
    setOf(CredentialDto(
        value = this.passowrd!!,
        temporary = false
    ))
)

fun UserEditProjection.toUserFetched(id: UUID) = UserFetched(
    id = id,
    username = this.email,
    email = this.email
)