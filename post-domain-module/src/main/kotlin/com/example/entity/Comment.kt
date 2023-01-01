package com.example.entity

import com.example.CommentProjection
import com.example.ReplyCreateProjection
import com.example.ReplyEdtProjection
import com.example.event.RemoveReplyEvent
import com.example.event.ReplyAddedEvent
import com.example.event.ReplyEditedEvent
import org.example.DDDEntity
import org.example.EntityNotFoundException
import java.time.LocalDateTime
import java.util.*

class Comment(
        val commentId: UUID? = null,
        val postId: UUID? = null,
        val authorId: UUID? = null,
        val text: String? = null,
        val sentTime: LocalDateTime? = null,
        private val _replies: MutableSet<Reply> = mutableSetOf()
): DDDEntity()  {

    override val entityId: UUID?
        get() = commentId

    public val replies: Set<Reply> get() = _replies.toSet();

    public fun editReply(reply: ReplyEdtProjection): ReplyEditedEvent {
        return replies.find { it.replyId == reply.replyId }?.let {replyToEdit ->
            _replies.removeIf { reply.replyId == it.replyId }
            _replies.add(Reply(
                replyId = reply.replyId,
                text = reply.text ?: "",
                sentTime = replyToEdit.sentTime
            ))
            ReplyEditedEvent(this)
        } ?: throw EntityNotFoundException(Reply::class.java, reply.replyId)


    }

    public fun addReply(reply: ReplyCreateProjection): ReplyAddedEvent {
        _replies.add( Reply(
            replyId = UUID.randomUUID(),
            text = reply.text ?: "",
            sentTime = LocalDateTime.now()
        ))
        return ReplyAddedEvent(this)
    }

    public fun removeReply(replyId: UUID): RemoveReplyEvent {
        _replies.removeIf { replyId == it.replyId }
        return RemoveReplyEvent(this)
    }

    fun toCommentProjection() = CommentProjection(
        commentId = commentId,
        authorId = authorId,
        text =  text,
        sentTime = sentTime ?: LocalDateTime.now(),
        replies =  replies.map { it.toReplyProjection() }
    )
}