package com.example.service

import com.example.GroupProjection
import com.example.PostProjection
import com.example.ProfileProjection
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam
import java.util.UUID

@FeignClient("post-app")
interface GroupServiceFeignClient {

    @GetMapping("/group/{groupId}")
    fun loadGroup(@PathVariable groupId: UUID): GroupProjection

    @GetMapping("/group/{groupId}/posts")
    fun loadGroupPosts(@PathVariable groupId: UUID, @RequestParam page: Int, @RequestParam size: Int): List<PostProjection>

    @GetMapping("/post")
    fun loadPosts(@RequestParam(required = true) profile: UUID, @RequestParam page: Int, @RequestParam size: Int): List<PostProjection>

    @GetMapping("/post/loadAllPosts")
    fun loadPostsWitGroupPosts(@RequestParam(required = true) profile: UUID, @RequestParam page: Int, @RequestParam size: Int): List<PostProjection>

}

@FeignClient("profile-app")
interface ProfileServiceFeignClient {

    @GetMapping("/profile/{profileId}")
    fun loadProfileData(@PathVariable profileId: UUID): ProfileProjection

}