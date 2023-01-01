package com.example.domainservice

import com.example.GroupPostCreateProjection
import com.example.GroupProjection
import com.example.PostProjection
import com.example.ProfileProjection
import java.util.*

interface GroupServiceClient {
    fun loadGroup(groupId: UUID): GroupProjection
    fun loadGroupPosts(groupId: UUID, size: Int, page: Int): List<PostProjection>
}


interface PostServiceClient {
    fun loadPosts(profile: UUID, page: Int, size: Int): List<PostProjection>
    fun loadPostsWitGroupPosts(profile: UUID, page: Int, size: Int): List<PostProjection>
}

interface ProfileServiceClient {
    fun loadProfileData(profile: UUID): ProfileProjection
}