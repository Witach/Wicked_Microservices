package com.example

import graphql.GraphQLContext
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.CrossOrigin
import java.util.*


@Controller
@CrossOrigin
class PostsResolver(val postAppService: PostAppService) {
    @MutationMapping
    fun createPost(@Argument input: PostCreatePorjection, graphQLContext: GraphQLContext): UUID? {
        postAppService.addPost(input, graphQLContext.getOrDefault("Authorization", ""))
        return null
    }

    @MutationMapping
    fun deletePost(@Argument postId: UUID, graphQLContext: GraphQLContext): UUID? {
        postAppService.deletePost(postId, graphQLContext.getOrDefault("Authorization", ""))
        return null
    }

    @MutationMapping
    fun updatePost(@Argument postId: UUID, @Argument input: UpdatePostProjection, graphQLContext: GraphQLContext): UUID? {
        postAppService.editPost(postId, input,graphQLContext.getOrDefault("Authorization", ""))
        return null
    }

    @MutationMapping
    fun deleteAttachment(@Argument postId: UUID, @Argument attachmentId: UUID, graphQLContext: GraphQLContext): UUID? {
        postAppService.deleteAttachment(postId, attachmentId, graphQLContext.getOrDefault("Authorization", ""))
        return null
    }
}