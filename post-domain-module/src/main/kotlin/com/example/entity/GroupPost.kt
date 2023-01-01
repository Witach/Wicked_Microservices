package com.example.entity

import java.time.LocalDateTime
import java.util.UUID

class GroupPost(
        val groupId: UUID,
        postId: UUID? = null,
        author: UUID? = null,
        text: String? = null,
        attachments: MutableList<Attachment> = mutableListOf(),
        sentTime: LocalDateTime? = null,
        _comments: MutableList<UUID> = mutableListOf()
) : Post(postId, author, text, attachments, sentTime, _comments) {
}