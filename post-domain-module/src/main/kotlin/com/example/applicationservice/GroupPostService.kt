package com.example.applicationservice

import com.example.GroupIds
import com.example.GroupPostCreateProjection
import com.example.PostProjection
import com.example.entity.Attachment
import com.example.entity.Group
import com.example.entity.GroupPost
import com.example.event.GroupPostAddedEvent
import com.example.event.GroupPostEditedEvent
import com.example.event.GroupPostRemovedEvent
import com.example.repository.*
import org.example.EntityNotFoundException
import org.example.EventPublisher
import org.example.NoAuthenticationAvailableException
import java.time.LocalDateTime
import java.util.*
import java.util.function.Predicate

class GroupPostService(
    private val groupPostRepository: GroupPostRepository,
    private val commentRepository: CommentRepository,
    private val groupRepository: GroupRepository,
    private val eventPublisher: EventPublisher,
    private val sessionStorage: SessionStorage,
    private val permissionPolicy: Predicate<Group>
) {

    fun addGroupPost(post: GroupPostCreateProjection) {
        val groupPost = groupPostRepository.save(
            GroupPost(
                postId = UUID.randomUUID(),
                groupId = post.group,
                author = post.author,
                text = post.text,
                attachments = post.attachments!!.map { Attachment(
                    attachmentId = UUID.randomUUID(),
                    resourceLink = it.resourceLink ?: "",
                    type = Attachment.AttachmentType.valueOf(it.type as String)
                ) }.toMutableList(),
                sentTime = LocalDateTime.now(),
            )
        )
        eventPublisher.publish(GroupPostAddedEvent(groupPost), "group-post-added-event")
    }

    fun removeGroupPost(postId: UUID) {
        groupPostRepository.findById(postId)?.also {
            it.postId?.let(groupPostRepository::deleteById)
            commentRepository.removeAllByPostId(postId)
            eventPublisher.publish(GroupPostRemovedEvent(it), "group-post-removed-event")
        } ?: throw EntityNotFoundException(GroupPost::class.java, postId)
    }

    fun editPostText(postUUID: UUID, text: String) {
        groupPostRepository.findById(postUUID)?.let {
            it.editText(text)
            val groupPost = groupPostRepository.save(it)
            eventPublisher.publish(GroupPostEditedEvent(groupPost), "group-post-edited-event")
        } ?: throw EntityNotFoundException(GroupPost::class.java, postUUID)
    }

    fun getGroupPostsById(groupId: UUID): List<PostProjection> {
        return groupRepository.findById(groupId)?.let {group ->
            if(permissionPolicy.test(group)) {
                groupPostRepository.findByGroupId(groupId).map { it.toPostProjection() }
            } else emptyList()
        } ?: throw NoAuthenticationAvailableException()
    }

    fun getGroupPosts(groupIds: List<UUID>): List<PostProjection> {
        return groupPostRepository.findAllByGroupIdAndCheckPermission(
            sessionStorage.sessionOwner.userId!!, groupIds)
            .map { it.toPostProjection() }
    }

}