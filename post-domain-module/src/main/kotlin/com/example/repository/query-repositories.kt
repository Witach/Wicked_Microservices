package com.example.repository

import com.example.entity.Comment
import com.example.entity.Group
import com.example.entity.GroupPost
import com.example.entity.Post
import org.example.EntityRepository
import java.util.*
import kotlin.collections.Set

interface CommentRepository: EntityRepository<Comment> {
    fun removeAllByPostId(postId: UUID)
}

interface GroupPostRepository: EntityRepository<GroupPost> {
    fun findByGroupId(groupId: UUID): Set<GroupPost>

    fun findAllByGroupIdAndCheckPermission(profile: UUID, groupIds: List<UUID>): List<GroupPost>
}

interface GroupRepository: EntityRepository<Group> {}

interface PostRepository: EntityRepository<Post>

interface AllPostsRepository {
    fun findAll(): List<Post>

    fun findAllByAuthorIn(profiles: Set<UUID>, page: Int, size: Int): List<Post>

    fun checkIfPostExsists(postId: UUID): Boolean

    fun feed(profiles: Set<UUID>, groups: List<UUID>, page: Int, size: Int): List<Post>
}