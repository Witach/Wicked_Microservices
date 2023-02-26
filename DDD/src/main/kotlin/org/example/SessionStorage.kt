package com.example.applicationservice

import java.util.*

interface SessionStorage {
    val sessionOwner: UserInfo
        get

    fun throwIfNoPermission(user: UUID) {
//        if(sessionOwner.userId != user) {
//            throw NoPermissionException()
//        }
    }

    data class UserInfo(val login: String, val userId: UUID?)
}
