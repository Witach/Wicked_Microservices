package com.example.entity

import com.example.AttachmentProjection
import org.example.DDDEntity
import java.util.UUID

class Attachment(val attachmentId: UUID,
                 val resourceLink: String,
                 val type: AttachmentType
): DDDEntity()  {

    override val entityId: UUID?
        get() = attachmentId

    enum class AttachmentType {
        IMAGE, FILE, VIDEO, TRACK
    }

    fun toAttachmentProjection() = AttachmentProjection(
        attachmentId = attachmentId,
        resourceLink = resourceLink,
        type = type.toString()
    )
}
