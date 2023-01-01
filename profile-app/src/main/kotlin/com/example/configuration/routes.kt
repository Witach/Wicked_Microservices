package com.example.configuration

import com.example.service.ProfileService
import com.example.service.UserService
import com.example.servicechassis.map
import org.example.RequiredParamsNotIncludedException
import org.example.toUUID
import org.springframework.web.servlet.function.*
import org.springframework.web.servlet.function.RequestPredicates.*
import org.springframework.web.servlet.function.RouterFunctions.nest
import org.springframework.web.servlet.function.RouterFunctions.route
import org.springframework.web.servlet.function.ServerResponse.*
import java.util.*

fun routes(profileService: ProfileService, userService: UserService): RouterFunction<ServerResponse>{
    return router {
        "profile".nest {
            PUT("{profileId}") {
                ok().body(
                    profileService.editProfile(
                        it.map("profileId"),
                        it.body()
                    )
                )
            }
            POST("{profileId}/group") {
                ok().body(
                    profileService.addToGroup(
                        it.map("profileId"),
                        it.body()
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
            "/user".nest {
                POST("") {
                    ok().body(
                        userService.registerNewUser(
                            it.body()
                        )
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
}