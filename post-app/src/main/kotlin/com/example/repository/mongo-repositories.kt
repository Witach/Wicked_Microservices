package com.example.repository

import org.springframework.data.domain.Pageable
import org.springframework.data.mongodb.repository.MongoRepository
import java.util.*

interface CommentJpaRepository: MongoRepository<CommentTable, UUID>

interface GroupJpaRepository: MongoRepository<GroupTable, UUID>

interface GroupPostJpaRepository: MongoRepository<GroupPostTable, UUID> {
    fun findAllByGroupId(id: UUID): List<GroupPostTable>
}

interface PostJpaRepository: MongoRepository<PostTable, UUID>

interface AllPostsJpaRepository: MongoRepository<PostTable, String>{

    fun  findAllByPostId(profile: UUID, page: Pageable): List<PostTable>
}