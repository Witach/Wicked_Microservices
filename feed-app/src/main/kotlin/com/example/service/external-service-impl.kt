package com.example.service

import com.example.GroupProjection
import com.example.PostProjection
import com.example.ProfileProjection
import com.example.domainservice.GroupServiceClient
import com.example.domainservice.PostServiceClient
import com.example.domainservice.ProfileServiceClient
import org.springframework.stereotype.Component
import java.util.*

@Component
class GroupServiceClientImpl(val groupServiceFeignClient: GroupServiceFeignClient): GroupServiceClient {
    override fun loadGroup(groupId: UUID): GroupProjection {
        return groupServiceFeignClient.loadGroup(groupId)
    }

    override fun loadGroupPosts(groupId: UUID, size: Int, page: Int): List<PostProjection> {
        return groupServiceFeignClient.loadGroupPosts(groupId, page, size)
    }
}

@Component
class PostServiceClientImpl(val groupServiceFeignClient: GroupServiceFeignClient): PostServiceClient {
    override fun loadPosts(profile: UUID, page: Int, size: Int): List<PostProjection> {
        return groupServiceFeignClient.loadPosts(profile, page, size)
    }

    override fun loadPostsWitGroupPosts(profile: UUID, page: Int, size: Int): List<PostProjection> {
        return groupServiceFeignClient.loadPosts(profile, page, size)
    }
}

@Component
class ProfileServiceClientImpl(val profileServiceFeignClient: ProfileServiceFeignClient): ProfileServiceClient {
    override fun loadProfileData(profile: UUID): ProfileProjection {
        return profileServiceFeignClient.loadProfileData(profile)
    }
}