package com.example.service

import com.example.*
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import java.util.UUID

@FeignClient("post-app")
interface GroupServiceFeignClient {

    @GetMapping("/group/{groupId}")
    fun loadGroup(@PathVariable groupId: UUID): GroupProjection

    @GetMapping("/group/post/posts/{groupId}")
    fun loadGroupPosts(@PathVariable groupId: UUID): List<PostProjection>

    @GetMapping("/post")
    fun loadPosts(@RequestParam proj: List<UUID>, @RequestParam page: Int, @RequestParam size: Int): List<PostProjection>

    @GetMapping("/feed")
    fun loadPostsWitGroupPosts(@RequestParam profiles: List<UUID>, @RequestParam groups: List<UUID>,
                               @RequestParam page: Int, @RequestParam size: Int): List<PostProjection>

}

@FeignClient("profile-app")
interface ProfileServiceFeignClient {

    @GetMapping("/profile/{profileId}")
    fun loadProfileData(@PathVariable profileId: UUID): ProfileProjection

}