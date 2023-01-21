package com.example

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.*
import java.util.*

@FeignClient("post-app")
interface PostAppService {

    @PostMapping("/comment")
    fun addComment(@RequestBody comment: CommentCreateProjection, @RequestHeader("Authorization") token: String)

    @PostMapping("/comment/{commentId}/reply")
    fun addReply(@RequestBody reply: ReplyCreateProjection, @PathVariable commentId: UUID, @RequestHeader("Authorization") token: String)

    @PutMapping("/comment/{commentId}/reply")
    fun editReply(@RequestBody reply: ReplyEdtProjection,
                  @PathVariable commentId: UUID,
                  @RequestHeader("Authorization") token: String)

    @DeleteMapping("/comment/{commentId}/reply/{replyId}")
    fun deleteReply(@PathVariable commentId: UUID,
                    @PathVariable replyId: UUID, @RequestHeader("Authorization") token: String)

    @DeleteMapping("/comment/{commentId}")
    fun deleteComment(@PathVariable commentId: UUID, @RequestHeader("Authorization") token: String)

    @GetMapping("/group/post/{postId}")
    fun getGroupPost(@PathVariable postId: UUID, @RequestHeader("Authorization") token: String): List<PostProjection>

    @PostMapping("/group/post")
    fun addGroupPost(@RequestBody post: GroupPostCreateProjection, @RequestHeader("Authorization") token: String)

    @PostMapping("/post")
    fun addPost(@RequestBody post: PostCreatePorjection, @RequestHeader("Authorization") token: String)

    @DeleteMapping("/post/{postId}")
    fun deletePost(@PathVariable postId: UUID, @RequestHeader("Authorization") token: String)

    @PutMapping("/post/{postId}")
    fun editPost(@PathVariable postId: UUID, @RequestBody post: UpdatePostProjection, @RequestHeader("Authorization") token: String)

    @DeleteMapping("/post/{postId}/attachment/{attachmentId}")
    fun deleteAttachment(@PathVariable postId: UUID, @PathVariable attachmentId: UUID, @RequestHeader("Authorization") token: String)

    @PostMapping("/group")
    fun addGroup(@RequestBody group: GroupCreateProjection, @RequestHeader("Authorization") token: String)

    @DeleteMapping("/group/{groupId}")
    fun deleteGroup(@PathVariable groupId: UUID, @RequestHeader("Authorization") token: String)

    @DeleteMapping("/group/{groupId}/profile/{profileId}")
    fun deleteMember(@PathVariable groupId: UUID, @PathVariable profileId: UUID, @RequestHeader("Authorization") token: String)

    @PostMapping("/group/{groupId}/profile")
    fun addMember(@PathVariable groupId: UUID, @RequestBody profile: ProfileProjectionWithFollow, @RequestHeader("Authorization") token: String)

    @GetMapping("/group/{groupId}")
    fun getGroup(@PathVariable groupId: UUID, @RequestHeader("Authorization") token: String): GroupProjection

}

@FeignClient(url = "http://localhost:8080", name = "profile-app", decode404 = true)
interface ProfileAppService {
    @GetMapping("/profile/{profileId}")
    fun getProfile(@PathVariable profileId: UUID, @RequestHeader("Authorization") token: String): ProfileProjection

    @GetMapping("/profile")
    fun getProfile(@RequestHeader("Authorization") token: String): List<ProfileProjection>

    @GetMapping("/profile/all")
    fun getProfiles(@RequestParam profiles: String, @RequestHeader("Authorization") token: String): List<ProfileProjection>
}

@FeignClient("feed-app")
interface FeedAppService {
    @GetMapping("/feed")
    fun getFeed(@RequestHeader("Authorization") token: String): List<PostProjection>

    @GetMapping("/feed/{profileId}/all")
    fun getFeed(@PathVariable profileId: UUID, @RequestHeader("Authorization") token: String): List<PostProjection>
}