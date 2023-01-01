package com.example.service

import com.example.GroupId
import com.example.RemoveGroupAssociations
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import java.util.*

@FeignClient("profile-app")
interface ProfileServiceFeignClient {

    @PostMapping("/profile/{profileId}/group")
    fun addGroupToProfile(@PathVariable profileId: UUID, @RequestBody groupToAdd: GroupId)

    @DeleteMapping("/profile/{profileId}/group")
    fun removeGroupFromProfile(@PathVariable profileId: UUID, @RequestBody groupId: GroupId)

    @DeleteMapping("/profile/removeGroupAssociations")
     fun removeGroupFromProfiles(@RequestBody data: RemoveGroupAssociations)
}