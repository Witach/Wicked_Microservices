package com.example.applicationservice

import com.example.GroupPostCreateProjection
import com.example.PostProjection
import com.example.entity.Attachment
import com.example.entity.GroupPost
import com.example.event.GroupPostAddedEvent
import com.example.event.GroupPostEditedEvent
import com.example.event.GroupPostRemovedEvent
import com.example.repository.*
import org.example.EntityNotFoundException
import org.example.EventPublisher
import org.example.NoAuthenticationAvailableException
import java.lang.IllegalArgumentException
import java.time.LocalDateTime
import java.util.*

public class GroupPostService(
    private val groupPostRepository: GroupPostRepository,
    private val commentRepository: CommentRepository,
    private val groupRepository: GroupRepository,
    private val sessionStorage: SessionStorage,
    private val eventPublisher: EventPublisher
) {

    fun addGroupPost(post: GroupPostCreateProjection) {
        val groupPost = groupPostRepository.save(
            GroupPost(
                groupId = post.group,
                author = post.author,
                text = post.text,
                attachments = post.attachments!!.map { Attachment(
                    it.attachmentId ?: throw IllegalArgumentException("Attachment Id not provided"),
                    it.resourceLink ?: "",
                    Attachment.AttachmentType.valueOf(it.type as String)
                ) }.toMutableList(),
                sentTime = LocalDateTime.now(),
            )
        )
        eventPublisher.publish(GroupPostAddedEvent(groupPost))
    }

    public fun removeGroupPost(postId: UUID) {
        groupPostRepository.findById(postId)?.also {
            it.postId?.let(groupPostRepository::deleteById)
            it.comments.let(commentRepository::removeAll)
            eventPublisher.publish(GroupPostRemovedEvent(it))
        } ?: throw EntityNotFoundException(GroupPost::class.java, postId)
    }

    public fun editPostText(postUUID: UUID, text: String) {
        groupPostRepository.findById(postUUID)?.let {
            it.editText(text)
            val groupPost = groupPostRepository.save(it)
            eventPublisher.publish(GroupPostEditedEvent(groupPost))
        } ?: throw EntityNotFoundException(GroupPost::class.java, postUUID)
    }

    public fun getGroupPosts(groupId: UUID): List<PostProjection> {
        return groupRepository.findById(groupId).let {group ->
            sessionStorage.sessionOwner.userId?.let { userId ->
                if(group?.containsProfile(userId) == true) {
                    groupPostRepository.findByGroupId(groupId).map { it.toPostProjection() }
                } else emptyList()
            }  ?: throw NoAuthenticationAvailableException()

        }
    }

}