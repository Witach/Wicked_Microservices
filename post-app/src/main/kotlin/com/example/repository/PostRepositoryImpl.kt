package com.example.repository

import com.example.entity.Post
import org.springframework.stereotype.Component
import java.util.*

@Component
class PostRepositoryImpl(private val postJpaRepository: PostJpaRepository): PostRepository {
    override fun save(e: Post): Post {
        return postJpaRepository.save(e.toPostTable()).toPost()
    }

    override fun deleteById(id: UUID) {
        postJpaRepository.deleteById(id)
    }

    override fun removeAll(ids: List<UUID>) {
        postJpaRepository.deleteAllById(ids)
    }

    override fun findById(id: UUID): Post? {
        return postJpaRepository.findById(id)
            .map { it.toPost() }
            .orElse(null)
    }

    override fun findAllByIds(ids: List<UUID>): List<Post> {
        return postJpaRepository.findAllById(ids)
            .map { it.toPost() }
    }

    override fun existById(uuid: UUID): Boolean {
        return postJpaRepository.existsById(uuid)
    }
}

fun PostTable.toPost(): Post {
    return Post(postId, author, text, attachments.map { it.toAttachment() }.toMutableList(), sentTime, comments.toMutableList())
}

fun Post.toPostTable(): PostTable {
    return PostTable(postId, author, text, attachments.map { it.toAttachmentTable() }, sentTime, comments.toList())
}