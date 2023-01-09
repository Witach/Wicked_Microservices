package com.example.repository

import com.example.entity.Attachment
import com.example.entity.GroupPost
import com.example.entity.Reply
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.DocumentReference
import java.time.LocalDateTime
import java.util.*

@Document("comments")
class CommentTable(@Id val commentId: UUID? = null,
              val postId: UUID? = null,
              val authorId: UUID? = null,
              val text: String? = null,
              val sentTime: LocalDateTime? = null,
              val replies: Set<ReplyTable> = setOf())

class ReplyTable(@Id val replyId: UUID,
                 val text: String,
                 val sentTime: LocalDateTime)

@Document("groups")
class GroupTable(@Id val groupInt: UUID,
            val name: String,
            val owner: UUID,
            val description: String,
            val image: String,
            val profiles: Set<UUID> = setOf(),
            val administrators: Set<UUID> = setOf())

@Document("groupPosts")
open class GroupPostTable(val groupId: UUID,
                     @Id val postId: UUID? = null,
                     val author: UUID? = null,
                     val text: String? = null,
                     val attachments: List<AttachmentTable> = listOf(),
                     val sentTime: LocalDateTime? = null)

data class GroupPostTableWithComments( val groupId: UUID,
                     @Id val postId: UUID? = null,
                     val author: UUID? = null,
                     val text: String? = null,
                     val attachments: List<AttachmentTable> = listOf(),
                     val sentTime: LocalDateTime? = null,
                     val comments: List<CommentTable> = listOf())

fun GroupPostTableWithComments.toGroupPost(): GroupPost {
    return GroupPost(groupId, postId, author, text, attachments.map { it.toAttachment() }
        .toMutableList(), sentTime, comments.map { it.toComment() }.toMutableList())
}

class PostTableWithComments(
    postId: UUID? = null,
    author: UUID? = null,
    text: String? = null,
    attachments: List<AttachmentTable> = listOf(),
    sentTime: LocalDateTime? = null,
    val comments: List<CommentTable> = listOf()
): PostTable(postId, author, text, attachments, sentTime)


@Document("posts")
open class PostTable(@Id val postId: UUID? = null,
                     val author: UUID? = null,
                     var text: String? = null,
                     val attachments: List<AttachmentTable> = listOf(),
                     val sentTime: LocalDateTime? = null)

class AttachmentTable(@Id val attachmentId: UUID,
                      val resourceLink: String,
                      val type: Attachment.AttachmentType)