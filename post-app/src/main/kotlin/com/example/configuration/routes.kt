package com.example.configuration

import abstractcom.example.applicationservice.CommentService
import com.example.GroupIds
import com.example.applicationservice.GroupPostService
import com.example.applicationservice.GroupService
import com.example.applicationservice.PostService
import com.example.servicechassis.map
import com.example.servicechassis.paraMap
import org.example.RequiredParamsNotIncludedException
import org.example.toUUID
import org.springframework.web.servlet.function.*
import org.springframework.web.servlet.function.RouterFunctions.route
import java.util.*

fun routes(commentService: CommentService, groupPostService: GroupPostService,
           groupService: GroupService, postService: PostService): RouterFunction<ServerResponse> {
    return router {
        path("/comment").nest {
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
        path("/group/post").nest {
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
            GET("/posts/{groupId}") {
                ServerResponse.ok().body(
                    groupPostService.getGroupPosts(
                        UUID.fromString(it.pathVariable("groupId")),
                    )
                )
            }
            GET("") {
                ServerResponse.ok().body(
                    groupPostService.getGroupPosts(
                        it.body<GroupIds>()
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
        path("/group").nest {
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
                    it.map("profileId"),
                    it.map("groupId")
                ))
            }
            GET("/{groupId}/exists") {
                ServerResponse.ok().body(mapOf(
                    "exists" to groupService.existsById(it.map("groupId"))
                ))
            }
        }
        path("/post").nest {
            GET("") {
                ServerResponse.ok().body(
                    postService.loadPostsPage(
                        it.body(),
                        it.paraMap("page") ?: 0,
                        it.paraMap("size") ?: 20
                    )
                )
            }
            POST("") {
                ServerResponse.ok().body(
                    postService.addPost(it.body())
                )
            }
            DELETE("/{postId}") {
                ServerResponse.ok().body(
                    postService.deletePost(it.map("postId"))
                )
            }
            PUT("/{postId}") {
                ServerResponse.ok().body(
                    postService.editPostText(
                        it.map("postId"),
                        it.body()
                    )
                )
            }
            DELETE("/{postId}/attachment/{attachmentId}") {
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
