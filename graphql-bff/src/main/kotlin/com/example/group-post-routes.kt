package com.example

import graphql.GraphQLContext
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.stereotype.Controller
import java.util.*

@Controller
class GroupPostResolver(val groupPostService: PostAppService) {

    @QueryMapping
    fun fetchGroupPost(@Argument groupId: UUID, graphQLContext: GraphQLContext): List<PostProjection> {
        return groupPostService.getGroupPost(groupId, graphQLContext.getOrDefault("Authorization", ""))
    }

    @MutationMapping
    fun addGroupPost(@Argument post: GroupPostCreateProjection, graphQLContext: GraphQLContext): UUID? {
        groupPostService.addGroupPost(post, graphQLContext.getOrDefault("Authorization", ""))
        return null
    }

}