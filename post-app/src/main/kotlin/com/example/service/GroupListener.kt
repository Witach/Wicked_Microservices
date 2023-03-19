package com.example.service

import com.example.GroupCreateProjection
import com.example.GroupId
import com.example.ProfileProjectionWithFollow
import com.example.applicationservice.GroupService
import com.example.servicechassis.*
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.messaging.Message
import org.springframework.messaging.handler.annotation.SendTo

class GroupListener(val kafkaObjectMapper: KafkaObjectMapper,
                    val groupService: GroupService,
                    val imperativeSessionStorage: ImperativeSessionStorage,
                    val kafkaAnswerTemplate: KafkaAnswerTemplate
) {

    @KafkaListener(topics = ["group-create-request"])
    @SendTo("group-create-response")
    fun `create-group-message`(content: ConsumerRecord<String, String>): Message<String> {
        return kafkaAnswerTemplate.answer(content) {
            groupService.addGroup(kafkaObjectMapper.readBody(content, GroupCreateProjection::class.java))
            SUCCESS
        }
    }

    @KafkaListener(topics = ["group-delete-request"])
    @SendTo("group-delete-response")
    fun `delete-group-message`(content: ConsumerRecord<String, String>): Message<String> {
        return kafkaAnswerTemplate.answer(content) {
            val map = kafkaObjectMapper.readPathVariable(content, "groupId")
            groupService.removeGroup(map)
            SUCCESS
        }
    }

    @KafkaListener(topics = ["group-exists-request"])
    @SendTo("group-exists-response")
    fun `group-exists-request`(content: ConsumerRecord<String, String>): Message<String> {
        return kafkaAnswerTemplate.answer(content) {
            val map = kafkaObjectMapper.readPathVariable(content, "groupId")
            groupService.existsById(map).toString()
        }
    }

    @KafkaListener(topics = ["group-profileadd-request"])
    @SendTo("group-profileadd-response")
    fun `addprofile-group-message`(record: ConsumerRecord<String, String>): Message<String> {
        return kafkaAnswerTemplate.answer(record) {
            val map = kafkaObjectMapper.readPathVariable(record, "groupId")
            groupService.addProfile(kafkaObjectMapper.readBody(record, ProfileProjectionWithFollow::class.java) , map)
            SUCCESS
        }
    }

    @KafkaListener(topics = ["group-profileremove-request"])
    @SendTo("group-profileremove-response")
    fun `removeprofile-group-message`(record: ConsumerRecord<String, String>): Message<String> {
        return kafkaAnswerTemplate.answer(record) {
            val map = kafkaObjectMapper.readPathVariable(record, "groupId")
            val profile = kafkaObjectMapper.readPathVariable(record, "profileId")
            groupService.removeProfile(profile, map)
            SUCCESS
        }
    }



    @KafkaListener(topics = ["group-get-request"])
    @SendTo("group-get-response")
    fun `group-get-response`(record: ConsumerRecord<String, String>): Message<String> {
        return kafkaAnswerTemplate.answer(record) {
            val map = kafkaObjectMapper.readPathVariable(record, "groupId")
            kafkaObjectMapper.convertToMessageFromBodyObject(groupService.fetchGroup(GroupId(map)))
        }
    }
}