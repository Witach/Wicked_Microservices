package com.example.service

import com.example.GroupCreateProjection
import com.example.GroupId
import com.example.ProfileProjectionWithFollow
import com.example.applicationservice.GroupService
import com.example.servicechassis.*
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.messaging.handler.annotation.SendTo
import java.util.*

class GroupListener(val kafkaObjectMapper: KafkaObjectMapper, val groupService: GroupService) {

    @KafkaListener(topics = ["group-create-request"])
    @SendTo("group-create-response")
    fun `create-group-message`(record: String): String {
        try {
            groupService.addGroup(kafkaObjectMapper.readBody(record, GroupCreateProjection::class.java))
        } catch (e: Exception) {
            return FAILURE
        }
        return SUCCESS
    }

    @KafkaListener(topics = ["group-delete-request"])
    @SendTo("group-delete-response")
    fun `delete-group-message`(record: String): String {
        val map = kafkaObjectMapper.readPathVariable(record, "groupId")
        try {
            groupService.removeGroup(map)
        } catch (e: Exception) {
            return FAILURE
        }
        return SUCCESS
    }

    @KafkaListener(topics = ["group-exists-request"])
    @SendTo("group-exists-response")
    fun `group-exists-request`(record: String): String {
        val map = kafkaObjectMapper.readPathVariable(record, "groupId")
        return try {
            groupService.existsById(map).toString()
        } catch (e: Exception) {
            FAILURE
        }
    }

    @KafkaListener(topics = ["group-profileadd-request"])
    @SendTo("group-profileadd-response")
    fun `addprofile-group-message`(record: String): String {
        val map = kafkaObjectMapper.readPathVariable(record, "groupId")
        try {
            groupService.addProfile(kafkaObjectMapper.readBody(record, ProfileProjectionWithFollow::class.java) , map)
        } catch (e: Exception) {
            return FAILURE
        }

        return SUCCESS
    }

    @KafkaListener(topics = ["group-profileremove-request"])
    @SendTo("group-profileremove-response")
    fun `removeprofile-group-message`(record: String): String {
        val map = kafkaObjectMapper.readPathVariable(record, "groupId")
        val profile = kafkaObjectMapper.readPathVariable(record, "profileId")
        try {
            groupService.removeProfile(map, profile)
        } catch (e: Exception) {
            return FAILURE
        }

        return SUCCESS
    }



    @KafkaListener(topics = ["group-get-request"])
    @SendTo("group-get-response")
    fun `group-get-response`(record: String): String {
        val map = kafkaObjectMapper.readPathVariable(record, "groupId")
        return try {
            kafkaObjectMapper.convertToMessageFromBodyObject(groupService.fetchGroup(GroupId(map)))
        } catch (e: Exception) {
            FAILURE
        }

    }
}