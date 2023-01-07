package com.example.applicationservice

import com.example.PostCreatePorjection
import com.example.PostProjection
import com.example.ProfileToSearchForProjection
import com.example.UpdatePostProjection
import com.example.entity.Attachment
import com.example.entity.Post
import com.example.event.PostAddedEvent
import com.example.event.PostRemovedEvent
import com.example.repository.AllPostsRepository
import com.example.repository.CommentRepository
import com.example.repository.PostRepository
import org.example.EntityNotFoundException
import org.example.EventPublisher
import java.time.LocalDateTime
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

    fun loadPostsPage(profiles: ProfileToSearchForProjection, page: Int = 0, size: Int = 20): List<PostProjection> {
        return allPostsRepository.findAllByAuthorIn(profiles.profiles.toSet(), page, size).map { it.toPostProjection() }
    }

    fun addPost(post: PostCreatePorjection) {
        val createdPost = repository.save(Post(
            postId = UUID.randomUUID(),
            author = post.author,
            text =  post.text,
            sentTime = LocalDateTime.now(),
            attachments = post.attachments?.map { Attachment(
                attachmentId = UUID.randomUUID(),
                resourceLink = it.resourceLink ?: "",
                type = Attachment.AttachmentType.valueOf(it.type.toString())
            ) }?.toMutableList() ?: mutableListOf()
        ))
        eventPublisher.publish(PostAddedEvent(createdPost), "post-created-event")
    }

    fun deletePost(postUUID: UUID) {
        repository.deleteById(postUUID)
        eventPublisher.publish(PostRemovedEvent(postUUID), "post-deleted-event")
    }

    fun editPostText(postUUID: UUID, post: UpdatePostProjection) {
        repository.findById(postUUID)?.also {
            post.text?.let { text ->
                val event = it.editText(post.text!!)
                eventPublisher.publish(event, "post-updated-event")
            }
            repository.save(it)
        } ?: throw EntityNotFoundException(Post::class.java, postUUID)
    }

    fun deletePostAttachment(postUUID: UUID, atachmentId: UUID) {

        repository.findById(postUUID)?.also {
            val event = it.removeAttachment(atachmentId)
            repository.save(it)
            eventPublisher.publish(event, "deleteattachment-post-message")
        } ?: throw EntityNotFoundException(Post::class.java, postUUID)
    }

    fun loadAllPosts(): List<PostProjection> {
        return allPostsRepository.findAll().map { it.toPostProjection() }
    }
}