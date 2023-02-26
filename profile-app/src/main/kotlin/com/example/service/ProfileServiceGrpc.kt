package com.example.service

import com.example.servicechassis.GRPCSessionStorage
import com.example.servicechassis.ImperativeSessionStorage
import com.examples.lib.*
import com.examples.lib.ProfileServiceGrpc.ProfileServiceImplBase
import io.grpc.stub.StreamObserver
import net.devh.boot.grpc.server.service.GrpcService
import org.example.toUUID
import org.springframework.context.annotation.Profile

@Profile("grpc")
@GrpcService
class ProfileServiceGrpc(val profileService: ProfileService,
                         val imperativeSessionStorage: GRPCSessionStorage
): ProfileServiceImplBase() {
    override fun addGroupToProfile(request: AddGroupToProfileMessage?, responseObserver: StreamObserver<Empty>?) {
        imperativeSessionStorage.userId = request!!.session.toUUID()
        profileService.addToGroup(request!!.profileId.toUUID(), request.groupToAdd.toUUID())
        responseObserver!!.onNext(Empty.newBuilder().build())
        responseObserver.onCompleted()
    }

    override fun removeGroupFromProfile(
        request: RemoveGroupFromProfileMessage?,
        responseObserver: StreamObserver<Empty>?
    ) {
        imperativeSessionStorage.userId = request!!.session.toUUID()
        profileService.removeFromGroup(request!!.profileId.toUUID(), request.groupId.toUUID())
        responseObserver!!.onNext(Empty.newBuilder().build())
        responseObserver.onCompleted()
    }

    override fun removeGroupFromProfiles(
        request: RemoveGroupFromProfileMessages?,
        responseObserver: StreamObserver<Empty>?
    ) {
        imperativeSessionStorage.userId = request!!.sessionId.toUUID()
        responseObserver!!.onNext(Empty.newBuilder().build())
        responseObserver.onCompleted()
    }

    override fun loadProfileData(
        request: LoadProfileDataMessage?,
        responseObserver: StreamObserver<LoadProfileDataResponse>?
    ) {
        imperativeSessionStorage.userId = request!!.session.toUUID()
        profileService.fetchUserProfile(request.profileId.toUUID())?.let {
            responseObserver!!.onNext(
                LoadProfileDataResponse.newBuilder()
                    .setProfileID(it.profileID.toString())
                    .setBirthday(it.birthday.toString())
                    .addAllFollowed(it.followed.map { it.toString() })
                    .addAllGroups(it.groups.map { it.toString() })
                    .setUserId(it.userId.toString())
                    .setUsername(it.username)
                    .build()
            )
            responseObserver.onCompleted()
        }
    }
}