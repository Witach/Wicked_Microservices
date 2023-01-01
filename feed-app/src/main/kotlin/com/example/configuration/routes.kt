package com.example.configuration

import com.example.service.GroupServiceClientImpl
import com.example.service.PostServiceClientImpl
import com.example.service.ProfileServiceClientImpl
import com.example.servicechassis.toUUID
import org.springframework.web.servlet.function.RouterFunction
import org.springframework.web.servlet.function.ServerResponse
import org.springframework.web.servlet.function.router

fun routes(groupServiceClientImpl: GroupServiceClientImpl,
           postServiceClientImpl: PostServiceClientImpl,
           profileServiceClientImpl: ProfileServiceClientImpl): RouterFunction<ServerResponse> {
    return router {
        "/feed".nest {
            GET("/{groupId}") {
                ok().body(groupServiceClientImpl.loadGroup(it.pathVariable("groupId").toUUID()))
            }
            GET("/{groupId}/post") {
                ok().body(
                    groupServiceClientImpl.loadGroupPosts(
                        it.pathVariable("groupId").toUUID(),
                        it.param("size").map(String::toInt).orElse(null),
                        it.param("page").map(String::toInt).orElse(1)
                    )
                )
            }
        }
        "/feed".nest {
            GET("/{profileId}") {
                ServerResponse.ok().body(
                    postServiceClientImpl.loadPosts(
                        it.pathVariable("profileId").toUUID(),
                        it.param("size").map(String::toInt).orElse(null),
                        it.param("page").map(String::toInt).orElse(1)
                    )
                )
            }
            GET("/{profileId}/all") {
                ServerResponse.ok().body(
                    postServiceClientImpl.loadPostsWitGroupPosts(
                        it.pathVariable("profileId").toUUID(),
                        it.param("size").map(String::toInt).orElse(null),
                        it.param("page").map(String::toInt).orElse(1)
                    )
                )
            }
        }
        GET("profile/feed/{profileId}") {
            ServerResponse.ok().body(
                profileServiceClientImpl.loadProfileData(
                    it.pathVariable("profileId").toUUID()
                )
            )
        }
    }
}