package com.example

import graphql.GraphQLContext
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.graphql.data.method.annotation.SchemaMapping
import org.springframework.stereotype.Controller
import java.util.*

@Controller
class GroupsResolver(val postAppService: PostAppService, val profileAppService: ProfileAppService) {

        @QueryMapping
        fun group(@Argument id: UUID, graphQLContext: GraphQLContext): GroupProjectionGraphQl {
            val groupProjection = postAppService.getGroup(id, graphQLContext.getOrDefault("Authorization", ""))
            graphQLContext.put("group", groupProjection)
            return GroupProjectionGraphQl(
                    groupInt = groupProjection.groupInt,
                    name = groupProjection.name,
                    description = groupProjection.description,
                    image = groupProjection.image,
                    posts = groupProjection.posts)
        }

        @SchemaMapping(field = "profiles", typeName = "GroupProjection")
        fun profiles(graphQLContext: GraphQLContext): List<ProfileProjectionQL>? {
            val groupProjection = graphQLContext.getOrDefault("group", null) as GroupProjection?
            if(groupProjection != null) {
                return profileAppService.getProfiles(groupProjection.profiles.joinToString(separator = ",") , graphQLContext.getOrDefault("Authorization", ""))
                    .map { profileProjection ->
                        ProfileProjectionQL(
                            profileID = profileProjection.profileID,
                            username = profileProjection.username,
                            birthday = profileProjection.birthday
                        )
                    }
            }
            return null
        }

        @SchemaMapping(field = "owner", typeName = "GroupProjection")
        fun owner(graphQLContext: GraphQLContext): ProfileProjectionQL? {
            val groupProjection = graphQLContext.getOrDefault("group", null) as GroupProjection?
            if(groupProjection != null) {
                val ownerProj = profileAppService.getProfile(groupProjection.owner, graphQLContext.getOrDefault("Authorization", ""))
                return ProfileProjectionQL(
                    profileID = ownerProj.profileID,
                    username = ownerProj.username,
                    birthday = ownerProj.birthday
                )
            }
            return null
        }

        @SchemaMapping(field = "administrators", typeName = "GroupProjection")
        fun administrators(graphQLContext: GraphQLContext): List<ProfileProjectionQL>? {
             val groupProjection = graphQLContext.getOrDefault("group", null) as GroupProjection?
            if(groupProjection != null) {
                return profileAppService.getProfiles(groupProjection.administrators.joinToString(separator = ",") , graphQLContext.getOrDefault("Authorization", ""))
                    .map { profileProjection ->
                        ProfileProjectionQL(
                            profileID = profileProjection.profileID,
                            username = profileProjection.username,
                            birthday = profileProjection.birthday
                        )
                    }
            }
            return null
        }

        @MutationMapping
        fun createGroup(@Argument group: GroupCreateProjection, graphQLContext: GraphQLContext): UUID? {
            postAppService.addGroup(group, graphQLContext.getOrDefault("Authorization", ""))
            return null
        }

        @MutationMapping
        fun deleteGroup(@Argument groupId: UUID, graphQLContext: GraphQLContext): UUID? {
            postAppService.deleteGroup(groupId, graphQLContext.getOrDefault("Authorization", ""))
            return null
        }

        @MutationMapping
        fun deleteMemberFromGroup(@Argument groupId: UUID, memberId: UUID, graphQLContext: GraphQLContext): Boolean {
            postAppService.deleteMember(groupId, memberId, graphQLContext.getOrDefault("Authorization", ""))
            return true
        }

        @MutationMapping
        fun addMemberToGroup(@Argument groupId: UUID, memberId: UUID, graphQLContext: GraphQLContext): Boolean {
            postAppService.addMember(groupId, ProfileProjectionWithFollow(memberId), graphQLContext.getOrDefault("Authorization", ""))
            return true
        }
}