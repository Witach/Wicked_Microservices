package com.example.domainservice

import java.util.UUID

class FeedService(
    private val feedSize: Int,
    private val groupServiceClient: GroupServiceClient,
    private val postServiceClient: PostServiceClient,
    private val profileServiceClient: ProfileServiceClient,
) {
    fun loadProfileFeed(profile: UUID, page: Int = 0): ProfileFeed {
        return ProfileFeed(
            posts = postServiceClient.loadPostsWitGroupPosts(profile, page, feedSize),
            profile = profileServiceClient.loadProfileData(profile)
        )
    }

    fun loadGroupFeed(group: UUID, page: Int = 0): GroupFeed {
        return GroupFeed(
            group = groupServiceClient.loadGroup(group),
            posts = groupServiceClient.loadGroupPosts(group, page, feedSize)
        )
    }
}