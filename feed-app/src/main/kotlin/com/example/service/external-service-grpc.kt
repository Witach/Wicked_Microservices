package com.example.service


import com.example.*
import com.example.applicationservice.SessionStorage
import com.example.domainservice.GroupServiceClient
import com.example.domainservice.PostServiceClient
import com.example.domainservice.ProfileServiceClient
import com.examples.lib.*
import com.examples.lib.ProfileServiceGrpc.ProfileServiceBlockingStub
import net.devh.boot.grpc.client.inject.GrpcClient
import org.example.toUUID
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*

@Profile("grpc")
@Component
class GroupServiceClientGrpc(val sessionStorage: SessionStorage): GroupServiceClient {

    @GrpcClient("postService") val groupServiceClient: GroupServiceGrpc.GroupServiceBlockingStub? = null
    override fun loadGroup(groupId: UUID): GroupProjection {
        val result = groupServiceClient!!.loadGroup(LoadGroupMessage.newBuilder()
            .setSession(sessionStorage.sessionOwner.userId.toString())
            .setGroupId(groupId.toString())
            .build())

        return result.toGroupProjection()
    }

    override fun loadGroupPosts(groupId: UUID): List<PostProjection> {
        val result = groupServiceClient!!.loadGroupPosts(LoadGroupPostsMessage.newBuilder()
            .setSession(sessionStorage.sessionOwner.userId.toString())
            .setGroupId(groupId.toString())
            .build())

        return result.postsList.map { it.toPostProjection() }
    }

    override fun loadPosts(profile: UUID, page: Int, size: Int): List<PostProjection> {
        val result = groupServiceClient!!.loadPosts(LoadPostsMessage.newBuilder()
            .setSession(sessionStorage.sessionOwner.userId.toString())
            .addProfiles(profile.toString())
            .setPage(page)
            .setSize(size)
            .build())

        return result.postsList.map { it.toPostProjection() }

    }

    override fun loadPostsWitGroupPosts(page: Int, size: Int): List<PostProjection> {
        val result = groupServiceClient!!.loadPostsWitGroupPosts(LoadPostsWithGroupPostsMessage.newBuilder()
            .setSession(sessionStorage.sessionOwner.userId.toString())
            .addProfiles(sessionStorage.sessionOwner.userId.toString())
            .setPage(page)
            .setSize(size)
            .build())

        return result.postsList.map { it.toPostProjection() }
    }
}

@Profile("grpc")
@Component
class PostServiceClientGrpc(val sessionStorage: SessionStorage): PostServiceClient {

    @GrpcClient("postService") val groupServiceClient: GroupServiceGrpc.GroupServiceBlockingStub? = null
    override fun loadPosts(profile: UUID, page: Int, size: Int): List<PostProjection> {
        val result = groupServiceClient!!.loadPosts(LoadPostsMessage.newBuilder()
            .setSession(sessionStorage.sessionOwner.userId.toString())
            .addProfiles(profile.toString())
            .setPage(page)
            .setSize(size)
            .build())

        return result.postsList.map { it.toPostProjection() }
    }

    override fun loadPostsWitGroupPosts(profile: UUID, page: Int, size: Int): List<PostProjection> {
        val result = groupServiceClient!!.loadPostsWitGroupPosts(LoadPostsWithGroupPostsMessage.newBuilder()
            .setSession(sessionStorage.sessionOwner.userId.toString())
            .setPage(page)
            .setSize(size)
            .build())

        return result.postsList.map { it.toPostProjection() }
    }
}

@Profile("grpc")
@Component
class ProfileServiceClientGrpc(val sessionStorage: SessionStorage): ProfileServiceClient {
    @GrpcClient("profileService") val profileService: ProfileServiceBlockingStub? = null
    override fun loadProfileData(profile: UUID): ProfileProjection {
        val result = profileService!!.loadProfileData(LoadProfileDataMessage.newBuilder()
            .setSession(sessionStorage.sessionOwner.userId.toString())
            .setProfileId(profile.toString())
            .build())

        return result.toProfileProjection()
    }
}

fun LoadGroupResponse.toGroupProjection() = GroupProjection (
    groupInt.toUUID(),
    name,
    description,
    image,
    owner.toUUID(),
    profilesList.map { it.toUUID() },
    postsList.map { post -> post.toPostProjection() },
    administratorsList.map { it.toUUID() }
)

fun PostProto.toPostProjection() = PostProjection(
    postId.toUUID(),
    author.toUUID(),
    text,
    attachmentsList.map { attachment -> attachment.toAttachmentProjection() },
    LocalDateTime.parse(sentTime),
    commentsList.map { comment -> comment.toCommentProjection() }
)

fun AttachmentProto.toAttachmentProjection() = AttachmentProjection(attachmentId.toUUID(), resourceLink, type)

fun CommentProto.toCommentProjection() = CommentProjection(commentId.toUUID(), authorId.toUUID(),
    text, LocalDateTime.parse(sentTime), repliesList.map {reply ->  reply.toReplyProjection()})

fun ReplyProto.toReplyProjection() = ReplyProjection(replyId.toUUID(), text, LocalDateTime.parse(order))

fun LoadProfileDataResponse.toProfileProjection() = ProfileProjection(
    profileID.toUUID(),
    username,
    LocalDate.parse(birthday),
    userId.toUUID(),
    groupsList.map { it.toUUID() }.toMutableSet(),
    followedList.map { it.toUUID() }.toMutableSet(),
)