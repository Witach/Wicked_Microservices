package com.example.repository

import org.springframework.data.domain.Pageable
import org.springframework.data.mongodb.repository.MongoRepository
import java.util.*

interface CommentJpaRepository: MongoRepository<CommentTable, UUID>

interface GroupJpaRepository: MongoRepository<GroupTable, UUID> {
    fun findAllByGroupIntInAndProfileIdInProfiles(list: List<UUID>, profileId: UUID, pageable: Pageable): List<GroupTable>
}

interface GroupPostJpaRepository: MongoRepository<GroupPostTable, UUID> {
    fun findAllByGroupId(id: UUID): List<GroupPostTable>
}

interface PostJpaRepository: MongoRepository<PostTable, UUID>

interface AllPostsJpaRepository: MongoRepository<PostTable, String>{

    fun  findAllByAuthorIn(profiles: Set<UUID>, page: Pageable): List<PostTable>
}