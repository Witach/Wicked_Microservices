package com.example.servicechassis

import com.example.applicationservice.SessionStorage
import org.springframework.security.core.context.SecurityContextHolder
import java.util.*

class SessionStorageImpl: SessionStorage {
    override val sessionOwner: SessionStorage.UserInfo
        get()   {
            SecurityContextHolder.getContext().authentication
            return  SessionStorage.UserInfo("", UUID.randomUUID());
        }
}