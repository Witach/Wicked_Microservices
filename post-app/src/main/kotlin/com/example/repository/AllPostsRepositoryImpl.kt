package com.example.repository

import com.example.entity.Post
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Component
import java.util.*

@Component
class AllPostsRepositoryImpl(val allPostsJpaRepository: AllPostsJpaRepository): AllPostsRepository {
    override fun findAll(profile: UUID, page: Int, size: Int): List<Post> {
        return allPostsJpaRepository.findAllByPostId(profile,
            PageRequest.of(page, size, Sort.by("sentTime")))
            .map { it.toPost() }
    }
}

