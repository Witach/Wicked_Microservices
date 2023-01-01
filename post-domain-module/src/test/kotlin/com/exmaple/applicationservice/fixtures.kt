package com.exmaple.applicationservice

import com.example.CommentCreateProjection
import com.example.GroupPostCreateProjection
import com.example.ReplyCreateProjection
import com.example.ReplyEdtProjection
import com.example.entity.Comment
import com.example.entity.Reply
import java.time.LocalDateTime
import java.util.*

fun comment(): Comment {
    return Comment(
        commentId = UUID.randomUUID(),
        authorId = UUID.randomUUID(),
        postId = UUID.randomUUID(),
        text = "DUPA",
        sentTime = LocalDateTime.now(),
    )
}

fun commentWithRelies(): Comment {
    return Comment(
        commentId = UUID.randomUUID(),
        authorId = UUID.randomUUID(),
        postId = UUID.randomUUID(),
        text = "DUPA",
        sentTime = LocalDateTime.now(),
        _replies = mutableSetOf(
            Reply(
                replyId = UUID.randomUUID(),
                text = "DUPA",
                sentTime = LocalDateTime.now()
            )
        )
    )
}

fun commentProjection(): CommentCreateProjection {
    return CommentCreateProjection(
        postId = UUID.randomUUID(),
        authorId = UUID.randomUUID(),
        text = "DUPA",
        sentTime = LocalDateTime.now()
    )
}

fun replyCreateProjection(): ReplyCreateProjection {
    return ReplyCreateProjection(
        text = "DUPA"
    )
}

fun replyEdtProjection(): ReplyEdtProjection {
    return ReplyEdtProjection(
        replyId = UUID.randomUUID(),
        text = "NIE_DUPA"
    )
}

fun groupPostCreateProjection(): GroupPostCreateProjection {
    return GroupPostCreateProjection(
        group = UUID.randomUUID(),
        author = UUID.randomUUID(),
        text = "DUPA"
    )
}