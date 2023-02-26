package com.example.service

import com.example.FeedSearch
import com.example.GroupId
import com.example.PostProjection
import com.example.applicationservice.GroupPostService
import com.example.applicationservice.GroupService
import com.example.applicationservice.PostService
import com.example.servicechassis.GRPCSessionStorage
import com.example.servicechassis.ImperativeSessionStorage
import com.examples.lib.*
import com.examples.lib.GroupServiceGrpc.GroupServiceImplBase
import io.grpc.stub.StreamObserver
import net.devh.boot.grpc.server.service.GrpcService;
import org.example.toUUID
import org.springframework.context.annotation.Profile


@Profile("grpc")
@GrpcService
class PostServiceGrpc(val groupService: GroupService,
                      val postService: PostService,
                      val imperativeSessionStorage: GRPCSessionStorage,
                      val groupPostService: GroupPostService): GroupServiceImplBase() {
    override fun loadGroup(request: LoadGroupMessage?, responseObserver: StreamObserver<LoadGroupResponse>?) {
        imperativeSessionStorage.userId = request!!.session.toUUID()
        groupService.fetchGroup(GroupId(request!!.groupId.toUUID())).let {
            responseObserver!!.onNext(LoadGroupResponse.newBuilder()
                .setGroupInt(it.groupInt.toString())
                .addAllPosts(it.posts!!.map { it.toPostProto() })
                .addAllProfiles(it.profiles!!.map { it.toString() })
                .addAllAdministrators(it.administrators!!.map { it.toString() })
                .setDescription(it.description)
                .setImage(it.image)
                .setName(it.name)
                .setOwner(it.owner.toString())
                .build())
            responseObserver.onCompleted()
        }
    }

    override fun loadGroupPosts(
        request: LoadGroupPostsMessage?,
        responseObserver: StreamObserver<LoadGroupPostsResponse>?
    ) {
        imperativeSessionStorage.userId = request!!.session.toUUID()
        groupPostService.getGroupPostsById(request!!.groupId.toUUID()).let {
            responseObserver!!.onNext(LoadGroupPostsResponse.newBuilder()
                .addAllPosts(it.map { post -> post.toPostProto() }).build())
            responseObserver.onCompleted()
        }
    }

    override fun loadPosts(request: LoadPostsMessage?, responseObserver: StreamObserver<LoadPostsResponse>?) {
        imperativeSessionStorage.userId = request!!.session.toUUID()
        postService.loadAllPosts(FeedSearch(
                request!!.profilesList.map { it.toUUID() }.toSet()
            ), request.page, request.size).let {
            responseObserver!!.onNext(LoadPostsResponse.newBuilder()
                .addAllPosts(it.map { post -> post.toPostProto() })
                .build())
            responseObserver.onCompleted()
        }
    }

    override fun loadPostsWitGroupPosts(
        request: LoadPostsWithGroupPostsMessage?,
        responseObserver: StreamObserver<LoadPostsWithGroupPostsResponse>?
    ) {
        imperativeSessionStorage.userId = request!!.session.toUUID()
        postService.loadAllPosts(FeedSearch(
            request!!.profilesList.map { it.toUUID() }.toSet(),
            request!!.groupsList.map { it.toUUID() }.toSet()
        ), request.page, request.size).let {
            responseObserver!!.onNext(LoadPostsWithGroupPostsResponse.newBuilder()
                .addAllPosts(it.map { post -> post.toPostProto()})
                .build())
            responseObserver.onCompleted()
        }
    }

    override fun existsById(request: ExistsByIdMessage?, responseObserver: StreamObserver<ExistsByIdResponse>?) {
        imperativeSessionStorage.userId = request!!.session.toUUID()
        groupService.existsById(request!!.groupId.toUUID()).let {
            responseObserver!!.onNext(ExistsByIdResponse.newBuilder()
                .setExists(it)
                .build())
            responseObserver.onCompleted()
        }
    }
}

fun PostProjection.toPostProto() = PostProto.newBuilder()
    .setPostId(postId.toString())
    .setText(text)
    .setSentTime(sentTime.toString())
    .setAuthor(author.toString())
    .addAllAttachments(attachments!!.map { attachment ->
        AttachmentProto.newBuilder()
            .setAttachmentId(attachment.attachmentId.toString())
            .setType(attachment.type)
            .setResourceLink(attachment.resourceLink)
            .build()
    }).build()