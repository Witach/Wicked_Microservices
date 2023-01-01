package com.example.entity

import com.example.PostProjection
import com.example.event.AttachmentAddedEvent
import com.example.event.AttachmentRemovedEvent
import com.example.event.PostEditedEvent
import org.example.DDDEntity
import java.time.LocalDateTime
import java.util.UUID

open class Post(
        val postId: UUID? = null,
        val author: UUID? = null,
        var text: String? = null,
        val attachments: MutableList<Attachment> = mutableListOf(),
        val sentTime: LocalDateTime? = null,
        private val _comments: MutableList<UUID> = mutableListOf()
): DDDEntity() {
    override val entityId: UUID?
        get() = postId

    val comments: List<UUID> get() = _comments.toList();

    fun addAttachment(attachment: Attachment): AttachmentAddedEvent {
        attachments.add(attachment)
        return AttachmentAddedEvent(this)
    }

    fun removeAttachment(attachment: UUID): AttachmentRemovedEvent {
        attachments.removeIf { attachment == it.attachmentId }
        return AttachmentRemovedEvent(this)
    }

    fun editText(text: String): PostEditedEvent {
        this.text = text
        return PostEditedEvent(this)
    }

    fun toPostProjection() = PostProjection(
        postId = postId,
        author = author,
        text = text,
        attachments = attachments.map { it.toAttachmentProjection() }
    )
}