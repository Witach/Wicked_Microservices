package com.example.service

import com.example.applicationservice.SessionStorage
import com.example.servicechassis.KafkaObjectMapper
import com.example.servicechassis.kafkaProxyFeign
import com.examples.lib.GroupServiceGrpc
import net.devh.boot.grpc.client.inject.GrpcClient
import org.springframework.context.annotation.Profile
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate
import org.springframework.stereotype.Component
import java.util.*


@Component
@Profile("feign")
class GroupServiceClientImpl(val groupServiceFeignClient: GroupServiceFeignClient): GroupServiceClient {
    override fun existsById(group: UUID): Boolean {
        return groupServiceFeignClient.existsById(group).exists
    }
}

@Component
@Profile("kafka")
class GroupServiceClientImplKafka(val kafkaClient: ReplyingKafkaTemplate<String, String, String>,
                                  val kafkaObjectMapper: KafkaObjectMapper): GroupServiceClient {
    override fun existsById(group: UUID): Boolean {
        val response = kafkaProxyFeign {
            kafkaTemplate = kafkaClient
            requestTopic = "group-exists-request"
            post = kafkaObjectMapper.convertToMessageFromPathVariable("groupId" to group.toString())
        }()
        return response == "true"
    }
}

@Component
@Profile("dumb")
class GroupClientDumbImpl(): GroupServiceClient {
    override fun existsById(group: UUID): Boolean {
        return true
    }
}

@Component
@Profile("grpc")
class GroupServiceClientImplGRPC(val sessionStorage: SessionStorage): GroupServiceClient {

    @GrpcClient("postService") val groupServiceClient: GroupServiceGrpc.GroupServiceBlockingStub? = null

    override fun existsById(group: UUID): Boolean {
        return groupServiceClient!!.existsById(
            com.examples.lib.ExistsByIdMessage.newBuilder()
                .setSession(sessionStorage.sessionOwner.userId.toString())
                .setGroupId(group.toString())
                .build()
        ).exists
    }
}