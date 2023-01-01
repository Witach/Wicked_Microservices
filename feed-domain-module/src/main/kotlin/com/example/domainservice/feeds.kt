package com.example.domainservice

import com.example.GroupProjection
import com.example.PostProjection
import com.example.ProfileProjection

data class ProfileFeed(
    val profile: ProfileProjection,
    val posts: List<PostProjection>
)

data class GroupFeed(
    val group: GroupProjection,
    val posts: List<PostProjection>
)
