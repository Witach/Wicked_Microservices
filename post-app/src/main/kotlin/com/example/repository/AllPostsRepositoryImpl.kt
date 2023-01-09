package com.example.repository

import com.example.entity.Post
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.aggregation.Aggregation
import org.springframework.data.mongodb.core.aggregation.Aggregation.*
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.stereotype.Component
import java.util.*

@Component
class AllPostsRepositoryImpl(val allPostsJpaRepository: AllPostsJpaRepository): AllPostsRepository {

    override fun findAll(): List<Post> {
        return allPostsJpaRepository.findAll().toList().map { it.toPost(mutableListOf()) }
    }

    override fun findAllByAuthorIn(profiles: Set<UUID>, page: Int, size: Int): List<Post> {
        return allPostsJpaRepository.findAllByAuthorIn(profiles,
            PageRequest.of(page, size, Sort.by("sentTime")))
            .map { it.toPost(mutableListOf()) }
    }
}

