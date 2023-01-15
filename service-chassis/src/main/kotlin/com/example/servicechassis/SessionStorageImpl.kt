package com.example.servicechassis

import com.example.applicationservice.SessionStorage
import org.example.toUUID
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.oauth2.jwt.Jwt
import java.util.*

class SessionStorageImpl: SessionStorage {
    override val sessionOwner: SessionStorage.UserInfo
        get()   {
            val auth = SecurityContextHolder.getContext().authentication
            return  SessionStorage.UserInfo((auth.principal as Jwt).claims["email"].toString(), auth.name.toUUID());
        }
}

class ImperativeSessionStorage: SessionStorage {
    var userId: UUID? = null
    override val sessionOwner: SessionStorage.UserInfo
        get()   {
            return SessionStorage.UserInfo("", userId)
        }
}

class SessionStorageMock: SessionStorage {
    override val sessionOwner: SessionStorage.UserInfo
        get()   {
            val auth = SecurityContextHolder.getContext().authentication
            return  SessionStorage.UserInfo((auth.principal as Jwt).claims["email"].toString(), auth.name.toUUID());
        }

    override fun throwIfNoPermission(user: UUID) {
        // do nothing
    }
}