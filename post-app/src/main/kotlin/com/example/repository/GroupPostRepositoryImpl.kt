package com.example.repository

import com.example.entity.Attachment
import com.example.entity.Comment
import com.example.entity.GroupPost
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.aggregation.Aggregation.*
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.stereotype.Component
import java.util.*

@Component
class GroupPostRepositoryImpl(private val groupPostJpaRepository: GroupPostJpaRepository,
    private val mongoTemplate: MongoTemplate): GroupPostRepository {
    override fun save(e: GroupPost): GroupPost {
        return groupPostJpaRepository.save(e.toGroupPostTable()).toGroupPost(e.comments)
    }

    override fun deleteById(id: UUID) {
        return groupPostJpaRepository.deleteById(id)
    }

    override fun removeAll(ids: List<UUID>) {
        return groupPostJpaRepository.deleteAllById(ids)
    }

    override fun findById(id: UUID): GroupPost? {
        return findAllByIds(listOf(id)).firstOrNull()
    }

    override fun findAllByIds(ids: List<UUID>): List<GroupPost> {
        val aggregation = newAggregation(
            match(Criteria.where("groupId").`in`(ids)),
            lookup("comments", "_id", "postId", "comments")
        ).withOptions(newAggregationOptions().allowDiskUse(true).build())

        return mongoTemplate.aggregate(aggregation, "groupPosts", GroupPostTableWithComments::class.java)
            .mappedResults
            .map { it.toGroupPost() }
    }

    override fun existById(uuid: UUID): Boolean {
        return groupPostJpaRepository.existsById(uuid)
    }

    override fun findByGroupId(groupId: UUID): Set<GroupPost> {
        return findAllByIds(listOf(groupId)).toSet()
    }

    override fun findAllByGroupIdAndCheckPermission(profile: UUID, groupIds: List<UUID>): List<GroupPost> {
        val aggregation =newAggregation(
            match(Criteria.where("groupId").`in`(groupIds)),
            lookup("groups", "groupId", "_id", "origin"),
            match(Criteria.where("origin.profiles").`in`(profile)),
            match(Criteria.where("origin").exists(true)),
            lookup("comments", "postId", "postId", "comments"),
        ).withOptions(newAggregationOptions().allowDiskUse(true).build())

        return mongoTemplate.aggregate(aggregation, "groupPosts", GroupPostTableWithComments::class.java)
            .mappedResults
            .map { it.toGroupPost() }
    }
}

fun GroupPostTable.toGroupPost(comments: List<Comment>): GroupPost {
    return GroupPost(groupId, postId, author, text,
        attachments.map { it.toAttachment() }.toMutableList(), sentTime, comments.toMutableList())
}

fun GroupPost.toGroupPostTable(): GroupPostTable {
    return GroupPostTable(groupId, postId, author, text,
        attachments.map { it.toAttachmentTable() }.toMutableList(), sentTime)
}

fun Attachment.toAttachmentTable(): AttachmentTable {
    return AttachmentTable(attachmentId, resourceLink, type)
}

fun AttachmentTable.toAttachment(): Attachment {
    return Attachment(attachmentId, resourceLink, type)
}