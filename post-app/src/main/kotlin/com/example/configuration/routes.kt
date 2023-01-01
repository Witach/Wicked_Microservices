package com.example.configuration

import abstractcom.example.applicationservice.CommentService
import com.example.applicationservice.GroupPostService
import com.example.applicationservice.GroupService
import com.example.applicationservice.PostService
import com.example.servicechassis.map
import com.example.servicechassis.paraMap
import org.example.RequiredParamsNotIncludedException
import org.example.toUUID
import org.springframework.web.servlet.function.*
import org.springframework.web.servlet.function.RouterFunctions.route

fun routes(commentService: CommentService, groupPostService: GroupPostService,
           groupService: GroupService, postService: PostService): RouterFunction<ServerResponse> {
    return router {
        "/comment".nest {
            POST("") {
                ServerResponse.ok().body(commentService.addComment(it.body()))
            }
            DELETE("/{commentId}") {
                ServerResponse.ok().body(commentService.deleteComment(it.map("commentId")))
            }
            POST("/{commentId}/reply") {
                ServerResponse.ok().body(
                    commentService.editReply(
                        it.map("commentId"),
                        it.body()
                    )
                )
            }
            PUT("/{commentId}/reply") {
                ServerResponse.ok().body(
                    commentService.editReply(
                        it.map("commentId"),
                        it.body()
                    )
                )
            }
            DELETE("/{commentId}/reply/{replyId}") {
                ServerResponse.ok().body(
                    commentService.removeReply(
                        it.map("commentId"),
                        it.map("replyId")
                    )
                )
            }
        }
        "/group/post".nest {
            POST("") {
                ServerResponse.ok().body(
                    groupPostService.addGroupPost(
                        it.body()
                    )
                )
            }
            DELETE("/{postId}") {
                ServerResponse.ok().body(
                    groupPostService.removeGroupPost(
                        it.map("postId")
                    )
                )
            }
            GET("/{postId}") {
                ServerResponse.ok().body(
                    groupPostService.getGroupPosts(
                        it.map("postId")
                    )
                )
            }
            PUT("/{postId}/{text}") {
                ServerResponse.ok().body(
                    groupPostService.editPostText(
                        it.map("postId"),
                        it.body()
                    )
                )
            }
        }
        "/group".nest {
            POST("") {
                ServerResponse.ok().body(groupService.addGroup(it.body()))
            }
            DELETE("/{groupId}") {
                ServerResponse.ok().body(groupService.removeGroup(
                    it.map("groupId")
                ))
            }
            POST("/{groupId}/profile") {
                ServerResponse.ok().body(groupService.addProfile(
                    it.body(),
                    it.map("groupId")
                ))
            }
            DELETE("/{groupId}/profile/{profileId}") {
                ServerResponse.ok().body(groupService.removeProfile(
                    it.map("groupId"),
                    it.map("groupId")
                ))
            }
            GET("/{groupId}/exists") {
                ServerResponse.ok().body(groupService.existsById(
                    it.map("groupId")
                ))
            }
        }
        "/post".nest {
            route(RequestPredicates.GET("")) {
                val profileId = it.paramOrNull("profileId")?.toUUID()
                    ?: throw RequiredParamsNotIncludedException(listOf("profileId"))
                ServerResponse.ok().body(
                    postService.loadPostsPage(
                        profileId,
                        it.paraMap("page") ?: 1,
                        it.paraMap("size") ?: 20
                    )
                )
            }.andRoute(RequestPredicates.POST("")) {
                ServerResponse.ok().body(
                    postService.addPost(it.body())
                )
            }.andRoute(RequestPredicates.DELETE("/{postId}")) {
                ServerResponse.ok().body(
                    postService.deletePost(it.map("postId"))
                )
            }.andRoute(RequestPredicates.PUT("/{postId}")) {
                ServerResponse.ok().body(
                    postService.editPostText(
                        it.map("postId"),
                        it.body()
                    )
                )
            }.andRoute(RequestPredicates.DELETE("/{postId}/attachment/{attachmentId}")) {
                ServerResponse.ok().body(
                    postService.deletePostAttachment(
                        it.map("postId"),
                        it.map("attachmentId")
                    )
                )
            }
        }
    }
}
