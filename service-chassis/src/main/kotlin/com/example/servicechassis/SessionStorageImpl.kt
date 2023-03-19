package com.example.servicechassis

import com.example.applicationservice.SessionStorage
import org.example.toUUID
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.oauth2.jwt.Jwt
import java.util.*
import javax.naming.NoPermissionException

class SessionStorageImpl: SessionStorage {
    override val sessionOwner: SessionStorage.UserInfo
        get()   {
            val auth = SecurityContextHolder.getContext().authentication
            if(auth.principal == "anonymousUser") {
                return SessionStorage.UserInfo("", UUID.randomUUID());
            } else {
                return  SessionStorage.UserInfo((auth.principal as Jwt).claims["email"].toString(), auth.name.toUUID());
            }
        }
}

class ImperativeSessionStorage: SessionStorage {
    var userId: UUID? = null
    override val sessionOwner: SessionStorage.UserInfo
        get()   {
            return SessionStorage.UserInfo("", userId)
        }
}

class GRPCSessionStorage: SessionStorage {
    var userId: UUID? = null
    override val sessionOwner: SessionStorage.UserInfo
        get()   {
            if(SecurityContextHolder.getContext().authentication != null) {
                val auth = SecurityContextHolder.getContext().authentication
                return SessionStorage.UserInfo((auth.principal as Jwt).claims["email"].toString(), auth.name.toUUID());
            }
            return userId?.let { SessionStorage.UserInfo("", it) } ?: throw NoPermissionException()
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