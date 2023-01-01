package com.example

import java.time.LocalDateTime
import java.util.*

data class CommentCreateProjection(
    val postId: UUID,
    val authorId: UUID? = null,
    val text: String? = null,
    val sentTime: LocalDateTime? = null)

data class CommentProjection(
    val commentId: UUID? = null,
    val authorId: UUID? = null,
    val text: String? = null,
    val sentTime: LocalDateTime,
    val replies: List<ReplyProjection> = listOf())

data class ReplyProjection(
    val replyId: UUID? = null,
    val text: String? = null,
    val order: LocalDateTime? = null)

data class ReplyCreateProjection(
    val text: String? = null
)
data class ReplyEdtProjection(
    var replyId: UUID,
    val text: String?
)