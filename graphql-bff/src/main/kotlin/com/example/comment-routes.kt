package com.example

import graphql.GraphQLContext
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.stereotype.Controller
import java.util.*

@Controller
class CommentResolver(val commentService: PostAppService) {

    @MutationMapping
    fun addComment(@Argument input: CommentCreateProjection, context: GraphQLContext): UUID? {
        commentService.addComment(input,  context.getOrDefault("Authorization", ""))
        return null
    }

    @MutationMapping
    fun addReply(@Argument commentId: UUID, @Argument input: ReplyCreateProjection, context: GraphQLContext): UUID? {
        commentService.addReply(input, commentId, context.getOrDefault("Authorization", ""))
        return null
    }

    @MutationMapping
    fun editReply(@Argument commentId: UUID, @Argument input: ReplyEdtProjection, context: GraphQLContext): UUID? {
        commentService.editReply(input, commentId, context.getOrDefault("Authorization", ""))
        return null
    }

    @MutationMapping
    fun deleteComment(@Argument commentId: UUID, context: GraphQLContext): UUID? {
        commentService.deleteComment(commentId, context.getOrDefault("Authorization", ""))
        return null
    }

    @MutationMapping
    fun deleteReply(@Argument commentId: UUID, @Argument replyId: UUID, context: GraphQLContext): UUID? {
        commentService.deleteReply(commentId, replyId, context.getOrDefault("Authorization", ""))
        return null
    }

}