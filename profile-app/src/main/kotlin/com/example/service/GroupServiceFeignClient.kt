package com.example.service

import com.example.ExistsById
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import java.util.UUID

@FeignClient("post-app")
interface GroupServiceFeignClient {

    @GetMapping("/group/{groupId}/exists")
    fun existsById(@PathVariable groupId: UUID): ExistsById
}