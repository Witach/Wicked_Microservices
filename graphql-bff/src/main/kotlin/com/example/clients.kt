package com.example

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.*
import java.util.*

@FeignClient("post-app")
interface CommentService {

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

}
