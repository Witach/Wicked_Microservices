package com.example.repository

import com.example.entity.Comment
import com.example.entity.Reply
import com.example.repository.CommentTable
import com.example.repository.ReplyTable
import org.springframework.stereotype.Component
import java.util.*

@Component
class CommentRepositoryImpl(private val commentRepository: CommentJpaRepository): CommentRepository {

    override fun save(e: Comment): Comment {
        return commentRepository.save(e.toCommentTable()).toComment()
    }

    override fun deleteById(id: UUID) {
        commentRepository.deleteById(id)
    }

    override fun removeAll(ids: List<UUID>) {
        commentRepository.deleteAllById(ids)
    }

    override fun findById(id: UUID): Comment? {
        return commentRepository.findById(id)
            .map { it.toComment() }
            .orElse(null)
    }

    override fun findAllByIds(ids: List<UUID>): List<Comment> {
        return commentRepository.findAllById(ids)
            .map { it.toComment() }
    }

    override fun existById(uuid: UUID): Boolean {
        return commentRepository.existsById(uuid)
    }

    override fun removeAllByPostId(postId: UUID) {
        commentRepository.removeAllByPostId(postId)
    }
}

fun Comment.toCommentTable(): CommentTable {
    return CommentTable(commentId, postId, authorId, text, sentTime, replies.map { it.toReplyTable() }.toSet())
}

fun CommentTable.toComment(): Comment {
    return Comment(commentId, postId, authorId, text, sentTime, replies.map { it.toReply() }.toMutableSet())
}

fun ReplyTable.toReply(): Reply {
    return Reply(replyId, text, sentTime)
}

fun Reply.toReplyTable(): ReplyTable {
    return ReplyTable(replyId, text, sentTime)
}