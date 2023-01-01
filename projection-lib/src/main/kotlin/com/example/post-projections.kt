package com.example

import java.time.LocalDateTime
import java.util.*

data class AttachmentProjection(
    val attachmentId: UUID? = null,
    val resourceLink: String? = null,
    val type: String? = null)

data class GroupProjection(
    val groupInt: UUID,
    val name: String,
    val description: String,
    val image: String,
    val owner: UUID,
    private val profiles: List<UUID> = mutableListOf(),
)

data class GroupPostCreateProjection(
    var group: UUID,
    var author: UUID,
    var text: String? = null,
    var attachments: List<AttachmentProjection>? = mutableListOf(),
    var sentTime: LocalDateTime? = null
)

data class GroupCreateProjection(
    val groupInt: UUID,
    val name: String,
    val description: String,
    val image: String,
    val owner: UUID,
)

data class PostProjection(
    var postId: UUID? = null,
    var author: UUID? = null,
    var text: String? = null,
    var attachments: List<AttachmentProjection>? = mutableListOf(),
    var sentTime: LocalDateTime? = null,
    var comments: List<CommentProjection> = mutableListOf())

data class PostCreatePorjection(
    var author: UUID? = null,
    var text: String? = null,
    var attachments: List<AttachmentProjection>? = mutableListOf(),
    var sentTime: LocalDateTime? = null
)
