package com.example

import graphql.GraphQLContext
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.CrossOrigin
import java.util.*


@Controller
@CrossOrigin
class FeedResolvers(val feedAppService: FeedAppService) {
    @QueryMapping
    fun feed(@Argument profileId: UUID, graphQLContext: GraphQLContext): List<PostProjection> {
        return feedAppService.getFeed(profileId,  graphQLContext.getOrDefault("Authorization", ""))
    }
}