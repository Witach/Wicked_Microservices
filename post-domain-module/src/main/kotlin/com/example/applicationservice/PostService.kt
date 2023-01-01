package com.example.applicationservice

import com.example.PostCreatePorjection
import com.example.PostProjection
import com.example.entity.Attachment
import com.example.entity.Post
import com.example.event.PostAddedEvent
import com.example.event.PostRemovedEvent
import com.example.repository.AllPostsRepository
import com.example.repository.CommentRepository
import com.example.repository.PostRepository
import org.example.EntityNotFoundException
import org.example.EventPublisher
import java.util.*

public class PostService(
    private val repository: PostRepository,
    private val commentRepository: CommentRepository,
    private val allPostsRepository: AllPostsRepository,
    private val eventPublisher: EventPublisher,
) {
    fun loadPost(postUUID: UUID): PostProjection {
        return repository.findById(postUUID)?.let {
            val comments = commentRepository.findAllByIds(it.comments)
            return it.toPostProjection().also {postProjection ->
                postProjection.comments = comments.map { comment -> comment.toCommentProjection() }
            }
        } ?: throw EntityNotFoundException(Post::class.java, postUUID)
    }

    fun loadPostsPage(profile: UUID, page: Int, size: Int = 20): List<PostProjection> {
        return allPostsRepository.findAll(profile, page, size).map { it.toPostProjection() }
    }

    fun addPost(post: PostCreatePorjection) {
        val createdPost = repository.save(Post(
            postId = UUID.randomUUID(),
            author = post.author,
            text =  post.text,
            attachments = post.attachments?.map { Attachment(
                attachmentId = UUID.randomUUID(),
                resourceLink = it.resourceLink ?: "",
                type = Attachment.AttachmentType.valueOf(it.type.toString())
            ) }?.toMutableList() ?: mutableListOf()
        ))
        eventPublisher.publish(PostAddedEvent(createdPost))
    }

    fun deletePost(postUUID: UUID) {
        repository.deleteById(postUUID)
        eventPublisher.publish(PostRemovedEvent(postUUID))
    }

    fun editPostText(postUUID: UUID, text: String) {
        repository.findById(postUUID)?.also {
            val edited = it.editText(text)
            repository.save(it)
            eventPublisher.publish(edited)
        } ?: throw EntityNotFoundException(Post::class.java, postUUID)
    }

    fun deletePostAttachment(postUUID: UUID, atachmentId: UUID) {

        repository.findById(postUUID)?.also {
            val event = it.removeAttachment(atachmentId)
            repository.save(it)
            eventPublisher.publish(event)
        } ?: throw EntityNotFoundException(Post::class.java, postUUID)
    }

    fun loadAllPosts(profile: UUID, page: Int, size: Int): List<PostProjection> {
        return allPostsRepository.findAll(profile, page, size).map { it.toPostProjection() }
    }
}