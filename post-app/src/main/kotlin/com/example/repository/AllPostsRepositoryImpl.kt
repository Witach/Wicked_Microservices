package com.example.repository

import com.example.entity.Post
import org.apache.kafka.common.protocol.types.Field.Str
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.aggregation.Aggregation.*
import org.springframework.data.mongodb.core.aggregation.UnionWithOperation.unionWith
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.stereotype.Component
import java.util.*

@Component
class AllPostsRepositoryImpl(val allPostsJpaRepository: AllPostsJpaRepository, val mongoTemplate: MongoTemplate): AllPostsRepository {

    override fun findAll(): List<Post> {
        return allPostsJpaRepository.findAll().toList().map { it.toPost(mutableListOf()) }


    }

    override fun findAllByAuthorIn(profiles: Set<UUID>, page: Int, size: Int): List<Post> {
        return allPostsJpaRepository.findAllByAuthorIn(profiles,
            PageRequest.of(page, size, Sort.by("sentTime")))
            .map { it.toPost(mutableListOf()) }
    }

    override fun feed(profiles: Set<UUID>, groups: List<UUID>, page: Int, size: Int): List<Post> {
        val aggregation = newAggregation(
            unionWith("groupPosts"),
            match(Criteria()
                .orOperator(Criteria.where("groupId").`in`(groups),
                    Criteria().andOperator(Criteria.where("author").`in`(profiles),
                        Criteria.where("groupId").exists(false)))),
            lookup("comments", "_id", "postId", "comments"),
            sort(Sort.Direction.DESC, "sentTime"),
            skip((page * size).toLong()),
            limit(size.toLong())
        ).withOptions(newAggregationOptions().allowDiskUse(true).build())

        return mongoTemplate.aggregate(aggregation, "posts", PostTableWithComments::class.java)
            .mappedResults
            .map { it.toPost(it.comments.map { comment -> comment.toComment() }) }
    }

    override fun checkIfPostExsists(postId: UUID): Boolean {
        val aggregation = newAggregation(
            unionWith("groupPosts"),
            match(Criteria.where("_id").`is`(postId)),
            count().`as`("count")
        ).withOptions(newAggregationOptions().allowDiskUse(true).build())
        return mongoTemplate.aggregate(aggregation, "posts", Map::class.java)
            .mappedResults
            .first()["count"] as Int > 0
    }
}

