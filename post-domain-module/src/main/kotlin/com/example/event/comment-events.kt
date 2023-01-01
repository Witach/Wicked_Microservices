package com.example.event

import com.example.entity.Comment
import org.example.DomainEvent
import java.util.UUID

abstract class CommentEvent: DomainEvent<Comment>()

class ReplyEditedEvent(val comment: Comment) : CommentEvent()

class ReplyAddedEvent(val comment: Comment): CommentEvent()

class RemoveReplyEvent(val comment: Comment): CommentEvent()

class CommentCreatedEvent(val comment: Comment): CommentEvent()

class CommentDeletedEvent(val commentId: UUID): CommentEvent()