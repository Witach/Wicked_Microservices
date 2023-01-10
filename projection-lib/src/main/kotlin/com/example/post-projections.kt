package com.example

import java.time.LocalDateTime
import java.util.*

data class AttachmentProjection(
    val attachmentId: UUID? = null,
    val resourceLink: String? = null,
    val type: String? = null)

data class GroupProjection(
    val groupInt: UUID,
    val name: String,
    val description: String,
    val image: String,
    val owner: UUID,
    val profiles: List<UUID> = mutableListOf(),
    val posts: List<PostProjection>? = null,
    val administrators: List<UUID> = mutableListOf()
)

data class GroupPostCreateProjection(
    var group: UUID,
    var author: UUID,
    var text: String? = null,
    var attachments: List<AttachmentProjection>? = mutableListOf(),
    var sentTime: LocalDateTime? = null
)

data class GroupCreateProjection(
    val groupInt: UUID?,
    val name: String?,
    val description: String?,
    val image: String?,
    val owner: UUID?,
)

data class PostProjection(
    var postId: UUID? = null,
    var author: UUID? = null,
    var text: String? = null,
    var attachments: List<AttachmentProjection>? = mutableListOf(),
    var sentTime: LocalDateTime? = null,
    var comments: List<CommentProjection> = mutableListOf())

data class PostCreatePorjection(
    var author: UUID? = null,
    var text: String? = null,
    var attachments: List<AttachmentProjection>? = mutableListOf(),
    var sentTime: LocalDateTime? = null
)

data class ProfileToSearchForProjection(
    val profiles: List<UUID> = mutableListOf()
)

data class UpdatePostProjection(
    var text: String? = null
)

data class GroupIds(
    val groupIds: List<UUID> = mutableListOf()
)

data class FeedSearch(
    val profiles: Set<UUID> = mutableSetOf(),
    val groups: Set<UUID> = mutableSetOf(),
)

data class FeedPostProjection (
    val postId: UUID,
    val author: UUID,
    val text: String,
    val attachments: List<AttachmentProjection>,
    val sentTime: LocalDateTime,
    val comments: List<CommentProjection>,
    val group: UUID?,
)


//db.groupPosts.aggregate([
//{ $match: { _id: { $in : [UUID("592a92ba-4c15-49f9-b5db-e4fdf40a12bc")]} } },
//{
//    $lookup: {
//    from: "groups",
//    localField: "groupId",
//    foreignField: "_id",
//    as: "origin",
//    pipeline: [
//    { $project: { profiles: 1, _id: 0 } },
//    { $match: { profiles: UUID("30c90205-5f18-4e3a-8e3c-40fda63038ee")}  },
//    { $unwind: "$profiles" },
//    ]
//},
//},
//{ $match: { origin: { $exists: true} } },
//{ $unset: "origin" }
//])
