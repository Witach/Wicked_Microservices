package com.example.configuration

import com.example.service.ProfileService
import com.example.service.UserService
import com.example.servicechassis.map
import org.example.RequiredParamsNotIncludedException
import org.example.toUUID
import org.springframework.web.servlet.function.RouterFunction
import org.springframework.web.servlet.function.ServerResponse
import org.springframework.web.servlet.function.body
import org.springframework.web.servlet.function.router

fun routes(profileService: ProfileService, userService: UserService): RouterFunction<ServerResponse>{
    return router {
        path("profile").nest {
            GET("/{profileId}") {
                ok().body(profileService.fetchUserProfile(it.map("profileId")))
            }
            GET("") {
                ok().body(profileService.fetchAllProfiles())
            }
            PUT("{profileId}") {
                ok().body(
                    profileService.editProfile(
                        it.map("profileId"),
                        it.body()
                    )
                )
            }
            POST("{profileId}/group") {
                val groupId = it.param("groupId")
                    .map { it.toUUID() }
                    .orElseThrow {
                        RequiredParamsNotIncludedException(listOf("groupId"))
                    }
                ok().body(
                    profileService.addToGroup(
                        it.map("profileId"),
                        groupId
                    )
                )
            }
            DELETE("/{profileId}/group") {
                val groupId = it.param("groupId")
                    .map { it.toUUID() }
                    .orElseThrow {
                        RequiredParamsNotIncludedException(listOf("groupId"))
                    }
                ok().body(
                    profileService.removeFromGroup(
                        it.map("profileId"),
                        groupId
                    )
                )
            }
            POST("{profileId}/follow") {
                ServerResponse.ok().body(
                    profileService.startToFollow(
                        it.map("profileId"),
                        it.body()
                    )
                )
            }
            DELETE("{profileId}/follow") {
                val profileToFollow = it.param("profileToFollow")
                    .map { it.toUUID() }
                    .orElseThrow {
                        RequiredParamsNotIncludedException(listOf("profileToFollow"))
                    }
                ServerResponse.ok().body(
                    profileService.removeFollowedProfile(
                        it.map("profileId"),
                        profileToFollow
                    )
                )
            }

        }
        path("/user").nest {
            POST("") {
                ok().body(
                    userService.registerNewUser(
                        it.body()
                    )
                )
            }
            GET("/{userId}") {
                ok().body(
                    userService.getUser(
                        it.map("userId")
                    )
                )
            }
            GET("") {
                ok().body(
                    userService.getAllUsers()
                )
            }
            PUT("/{userId}") {
                ok().body(
                    userService.editUser(
                        it.map("userId"),
                        it.body()
                    )
                )
            }
            DELETE("/{userId}") {
                ok().body(
                    userService.removeUser(
                        it.map("userId")
                    )
                )
            }
        }
    }
}