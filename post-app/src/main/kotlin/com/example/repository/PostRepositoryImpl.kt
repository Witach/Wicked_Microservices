package com.example.repository

import com.example.entity.Comment
import com.example.entity.Post
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.aggregation.Aggregation
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.stereotype.Component
import java.util.*

@Component
class PostRepositoryImpl(private val postJpaRepository: PostJpaRepository, val mongoTemplate: MongoTemplate): PostRepository {
    override fun save(e: Post): Post {
        return postJpaRepository.save(e.toPostTable()).toPost(e.comments)
    }

    override fun deleteById(id: UUID) {
        postJpaRepository.deleteById(id)
    }

    override fun removeAll(ids: List<UUID>) {
        postJpaRepository.deleteAllById(ids)
    }

    override fun findById(id: UUID): Post? {
        return findAllByIds(listOf(id)).firstOrNull()
    }

    override fun findAllByIds(ids: List<UUID>): List<Post> {
        val aggregation = Aggregation.newAggregation(
            Aggregation.match(Criteria.where("_id").`in`(ids)),
            Aggregation.lookup("comments", "_id", "postId", "comments")
        ).withOptions(Aggregation.newAggregationOptions().allowDiskUse(true).build())

        return mongoTemplate.aggregate(aggregation, "posts", PostTableWithComments::class.java)
            .mappedResults
            .map { it.toPost(it.comments.map { comment -> comment.toComment() }) }
    }

    override fun existById(uuid: UUID): Boolean {
        return postJpaRepository.existsById(uuid)
    }
}

fun PostTable.toPost(comment: List<Comment>): Post {
    return Post(postId, author, text, attachments.map { it.toAttachment() }.toMutableList(), sentTime, comment.toMutableList())
}

fun Post.toPostTable(): PostTable {
    return PostTable(postId, author, text, attachments.map { it.toAttachmentTable() }, sentTime)
}