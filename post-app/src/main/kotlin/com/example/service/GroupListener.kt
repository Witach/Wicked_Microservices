package com.example.service

import com.example.GroupCreateProjection
import com.example.GroupId
import com.example.ProfileProjectionWithFollow
import com.example.applicationservice.GroupService
import com.example.servicechassis.FAILURE
import com.example.servicechassis.ImperativeSessionStorage
import com.example.servicechassis.KafkaObjectMapper
import com.example.servicechassis.SUCCESS
import org.springframework.kafka.annotation.KafkaListener

class GroupListener(val kafkaObjectMapper: KafkaObjectMapper, val groupService: GroupService, val imperativeSessionStorage: ImperativeSessionStorage) {

    @KafkaListener(topics = ["group-create-request"])
    fun `create-group-message`(record: String): String {
        imperativeSessionStorage.userId = kafkaObjectMapper.readSession(record)
        try {
            groupService.addGroup(kafkaObjectMapper.readBody(record, GroupCreateProjection::class.java))
        } catch (e: Exception) {
            return FAILURE
        }
        return SUCCESS
    }

    @KafkaListener(topics = ["group-delete-request"])
    fun `delete-group-message`(record: String): String {
        imperativeSessionStorage.userId = kafkaObjectMapper.readSession(record)
        val map = kafkaObjectMapper.readPathVariable(record, "groupId")
        try {
            groupService.removeGroup(map)
        } catch (e: Exception) {
            return FAILURE
        }
        return SUCCESS
    }

    @KafkaListener(topics = ["group-exists-request"])
    fun `group-exists-request`(record: String): String {
        imperativeSessionStorage.userId = kafkaObjectMapper.readSession(record)
        val map = kafkaObjectMapper.readPathVariable(record, "groupId")
        return try {
            groupService.existsById(map).toString()
        } catch (e: Exception) {
            FAILURE
        }
    }

    @KafkaListener(topics = ["group-profileadd-request"])
    fun `addprofile-group-message`(record: String): String {
        imperativeSessionStorage.userId = kafkaObjectMapper.readSession(record)
        val map = kafkaObjectMapper.readPathVariable(record, "groupId")
        try {
            groupService.addProfile(kafkaObjectMapper.readBody(record, ProfileProjectionWithFollow::class.java) , map)
        } catch (e: Exception) {
            return FAILURE
        }

        return SUCCESS
    }

    @KafkaListener(topics = ["group-profileremove-request"])
    fun `removeprofile-group-message`(record: String): String {
        imperativeSessionStorage.userId = kafkaObjectMapper.readSession(record)
        val map = kafkaObjectMapper.readPathVariable(record, "groupId")
        val profile = kafkaObjectMapper.readPathVariable(record, "profileId")
        try {
            groupService.removeProfile(profile, map)
        } catch (e: Exception) {
            return FAILURE
        }

        return SUCCESS
    }



    @KafkaListener(topics = ["group-get-request"])
    fun `group-get-response`(record: String): String {
        imperativeSessionStorage.userId = kafkaObjectMapper.readSession(record)
        val map = kafkaObjectMapper.readPathVariable(record, "groupId")
        return try {
            kafkaObjectMapper.convertToMessageFromBodyObject(groupService.fetchGroup(GroupId(map)))
        } catch (e: Exception) {
            FAILURE
        }

    }
}