package com.example.repository

import com.example.entity.Attachment
import com.example.entity.GroupPost
import com.example.repository.AttachmentTable
import com.example.repository.GroupPostTable
import org.springframework.stereotype.Component
import java.util.*

@Component
class GroupPostRepositoryImpl(private val groupPostJpaRepository: GroupPostJpaRepository): GroupPostRepository {
    override fun save(e: GroupPost): GroupPost {
        return groupPostJpaRepository.save(e.toGroupPostTable()).toGroupPost()
    }

    override fun deleteById(id: UUID) {
        return groupPostJpaRepository.deleteById(id)
    }

    override fun removeAll(ids: List<UUID>) {
        return groupPostJpaRepository.deleteAllById(ids)
    }

    override fun findById(id: UUID): GroupPost? {
        return groupPostJpaRepository.findById(id)
            .map { it.toGroupPost() }
            .orElse(null)
    }

    override fun findAllByIds(ids: List<UUID>): List<GroupPost> {
        return groupPostJpaRepository.findAllById(ids)
            .map { it.toGroupPost() }
    }

    override fun existById(uuid: UUID): Boolean {
        return groupPostJpaRepository.existsById(uuid)
    }

    override fun findByGroupId(groupId: UUID): Set<GroupPost> {
        return groupPostJpaRepository.findAllByGroupId(groupId)
            .map { it.toGroupPost() }
            .toSet()
    }
}

fun GroupPostTable.toGroupPost(): GroupPost {
    return GroupPost(groupId, postId, author, text,
        attachments.map { it.toAttachment() }.toMutableList(), sentTime, comments.toMutableList())
}

fun GroupPost.toGroupPostTable(): GroupPostTable {
    return GroupPostTable(groupId, postId, author, text,
        attachments.map { it.toAttachmentTable() }.toMutableList(), sentTime, comments)
}

fun Attachment.toAttachmentTable(): AttachmentTable {
    return AttachmentTable(attachmentId, resourceLink, type)
}

fun AttachmentTable.toAttachment(): Attachment {
    return Attachment(attachmentId, resourceLink, type)
}