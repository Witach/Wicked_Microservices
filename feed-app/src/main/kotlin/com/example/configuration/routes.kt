package com.example.configuration

import com.example.domainservice.GroupServiceClient
import com.example.domainservice.PostServiceClient
import com.example.servicechassis.toUUID
import org.springframework.web.servlet.function.RouterFunction
import org.springframework.web.servlet.function.ServerResponse
import org.springframework.web.servlet.function.router

fun routes(groupServiceClientImpl: GroupServiceClient,
           postServiceClientImpl: PostServiceClient): RouterFunction<ServerResponse> {
    return router {
        path("/feed/group").nest {
            GET("/{groupId}") {
                ok().body(groupServiceClientImpl.loadGroup(it.pathVariable("groupId").toUUID()))
            }
            GET("/{groupId}/post") {
                ok().body(
                    groupServiceClientImpl.loadGroupPosts(
                        it.pathVariable("groupId").toUUID()
                    )
                )
            }
        }
        path("/feed/profile").nest {
            GET("/{profileId}") {
                ServerResponse.ok().body(
                    postServiceClientImpl.loadPosts(
                        it.pathVariable("profileId").toUUID(),
                        it.param("page").map(String::toInt).orElse(0),
                        it.param("size").map(String::toInt).orElse(20)
                    )
                )
            }
            GET("/{profileId}/all") {
                ServerResponse.ok().body(
                    postServiceClientImpl.loadPostsWitGroupPosts(
                        it.pathVariable("profileId").toUUID(),
                        it.param("page").map(String::toInt).orElse(0),
                        it.param("size").map(String::toInt).orElse(20)
                    )
                )
            }
        }
        GET("/feed/{profileId}/all") {
            ServerResponse.ok().body(
                groupServiceClientImpl.loadPostsWitGroupPosts(
                    it.param("page").map(String::toInt).orElse(0),
                    it.param("size").map(String::toInt).orElse(20)
                )
            )
        }
    }
}