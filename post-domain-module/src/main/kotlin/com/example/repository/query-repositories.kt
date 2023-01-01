package com.example.repository

import com.example.entity.Comment
import com.example.entity.Group
import com.example.entity.GroupPost
import com.example.entity.Post
import org.example.EntityRepository
import java.util.*
import kotlin.collections.Set

interface CommentRepository: EntityRepository<Comment>

interface GroupPostRepository: EntityRepository<GroupPost> {
    fun findByGroupId(groupId: UUID): Set<GroupPost>
}

interface GroupRepository: EntityRepository<Group>

interface PostRepository: EntityRepository<Post>

interface AllPostsRepository {
    fun findAll(profile: UUID, page: Int, size: Int): List<Post>
}