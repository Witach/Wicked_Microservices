package com.example.service

import com.example.*
import com.example.applicationservice.SessionStorage
import com.example.domainservice.GroupServiceClient
import com.example.domainservice.PostServiceClient
import com.example.domainservice.ProfileServiceClient
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component
import java.util.*

@Profile("feign")
@Component
class GroupServiceClientImpl(val groupServiceFeignClient: GroupServiceFeignClient,
                             val profileServiceFeignClient: ProfileServiceFeignClient,
                             val sessionStorage: SessionStorage
): GroupServiceClient {
    override fun loadGroup(groupId: UUID): GroupProjection {
        return groupServiceFeignClient.loadGroup(groupId)
    }

    override fun loadGroupPosts(groupId: UUID): List<PostProjection> {
        return groupServiceFeignClient.loadGroupPosts(groupId)
    }

    override fun loadPosts(profile: UUID, page: Int, size: Int): List<PostProjection> {
        return groupServiceFeignClient.loadPosts(mutableListOf(profile), page, size)
    }

    override fun loadPostsWitGroupPosts(page: Int, size: Int): List<PostProjection> {

        val profileData = profileServiceFeignClient.loadProfileData(sessionStorage.sessionOwner.userId!!)
        return groupServiceFeignClient.loadPostsWitGroupPosts(profileData.followed.toMutableList(),
            profileData.groups.toMutableList(), page, size)
    }
}

@Profile("feign")
@Component
class PostServiceClientImpl(val groupServiceFeignClient: GroupServiceFeignClient): PostServiceClient {
    override fun loadPosts(profile: UUID, page: Int, size: Int): List<PostProjection> {
         return groupServiceFeignClient.loadPosts(mutableListOf(profile), page, size)
    }

    override fun loadPostsWitGroupPosts(profile: UUID, page: Int, size: Int): List<PostProjection> {
        return groupServiceFeignClient.loadPosts(mutableListOf(profile), page, size)
    }
}

@Profile("feign")
@Component
class ProfileServiceClientImpl(val profileServiceFeignClient: ProfileServiceFeignClient): ProfileServiceClient {
    override fun loadProfileData(profile: UUID): ProfileProjection {
        return profileServiceFeignClient.loadProfileData(profile)
    }
}