package com.example.entity

import com.example.ReplyProjection
import org.example.DDDEntity
import java.time.LocalDateTime
import java.util.UUID

class Reply (
        val replyId: UUID,
        val text: String,
        val sentTime: LocalDateTime
): DDDEntity()  {
    override val entityId: UUID?
        get() = replyId

    fun toReplyProjection() = ReplyProjection(
        replyId = replyId,
        text = text,
        order = sentTime
    )
}
