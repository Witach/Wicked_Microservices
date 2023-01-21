package com.example

import graphql.GraphQLContext
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.stereotype.Controller
import java.util.*

@Controller
class ProfileResolvers(val profileAppService: ProfileAppService) {

    @QueryMapping
    fun fetchProfile(@Argument profileId: UUID, graphQLContext: GraphQLContext): ProfileProjection {
        return profileAppService.getProfile(profileId, graphQLContext.getOrDefault("Authorization", ""))
    }

    @QueryMapping
    fun fetchAllProfiles(graphQLContext: GraphQLContext): List<ProfileProjection> {
        return profileAppService.getProfile(graphQLContext.getOrDefault("Authorization", ""))
    }

}