package com.example.servicechassis

import com.example.applicationservice.SessionStorage
import com.fasterxml.jackson.databind.ObjectMapper
import org.apache.kafka.clients.consumer.ConsumerRecord
import java.util.*


val BODY_KEY = "body"
val PARAM_KEY = "param"
val PATH_VARIABLE_KEY = "pathVariable"
val SUCCESS = "success"
val FAILURE = "faiil"
val PAGE = "page"
val SIZE = "size"
val USER = "user"


data class MessageSync(var body: Any, var param: Map<String, Any>, var pathVariable: Map<String, Any>)
data class MessageSync2<T>(var body: T)



class KafkaObjectMapper(val objectMapper: ObjectMapper, val sessionStorage: SessionStorage) {

    fun < T: Any> readBody(consumerRecord: String, klass: Class<T>): T {
        val str = objectMapper.readTree(consumerRecord)[BODY_KEY].toString()
        return objectMapper.readValue(str, klass)
    }

    fun <T: Any> readParameter(consumerRecord: String, param: String, klass: Class<T>): T? {
        val firstMap = objectMapper.readValue(consumerRecord, Map::class.java)
        firstMap[PARAM_KEY] ?: return null
        val secondMap = (firstMap[PARAM_KEY] as Map <String, String>)[param]
        return objectMapper.readValue(secondMap as String, klass)
    }

    fun readParameterUUID(consumerRecord: String, param: String): UUID {
        val firstMap = objectMapper.readValue(consumerRecord, Map::class.java)
        val secondMap = (firstMap[PARAM_KEY] as Map <String, String>)[param]
        return UUID.fromString(secondMap as String)
    }


    fun readPathVariable(consumerRecord: String, param: String): UUID {
        val firstMap = objectMapper.readTree(consumerRecord)[PATH_VARIABLE_KEY].toString()
        val secondMap = objectMapper.readValue(firstMap, Map::class.java)[param]
        return UUID.fromString(secondMap as String)
    }


    fun <T> readParamList(consumerRecord: String, param: String, klass: Class<*>): List<UUID> {
        val firstMap = objectMapper.readTree(consumerRecord)[PARAM_KEY].toString()
        val secondMap = objectMapper.readValue(firstMap, Map::class.java)[param] as String
        return secondMap
            .split(",")
            .map { it.trim() as String }
            .map { UUID.fromString(it) }
            .toList()
    }

    fun readSession(record: String): UUID {
        val firstMap = objectMapper.readTree(record)[USER].toString().replace("\"", "")
        return UUID.fromString(firstMap)
    }

    fun convertToMessageFromBody(body: String): String {
        return objectMapper.writeValueAsString(
            mapOf(
                BODY_KEY to body,
                USER to sessionStorage.sessionOwner.userId

            )
        )
    }

    fun convertToMessageFromBodyObject(body: Any): String {
        return objectMapper.writeValueAsString(
            mapOf(
                BODY_KEY to body,
                USER to sessionStorage.sessionOwner.userId
            )
        )
    }

    fun convertToMessageFrom(init: MessageSync.() -> Unit): String {
        val messageSync = MessageSync("", mapOf(), mapOf())
        messageSync.init()
        return objectMapper.writeValueAsString(
            mapOf(
                BODY_KEY to messageSync.body,
                PARAM_KEY to messageSync.param,
                PATH_VARIABLE_KEY to messageSync.pathVariable,
                USER to sessionStorage.sessionOwner.userId
            )
        )
    }

    fun convertToMessageFromPathVariable(pathvariable: Pair<String, Any>): String {
        return objectMapper.writeValueAsString(
            mapOf(
                PATH_VARIABLE_KEY to mapOf(pathvariable),
                USER to sessionStorage.sessionOwner.userId
            )
        )
    }

    fun consumerRecordToMap(record: ConsumerRecord<String, String>): Map<String, String> {
        return objectMapper.readValue(record.value(), Map::class.java) as Map<String, String>
    }

}

fun tryToResponse(funx: () -> String): String {
    return try {
        funx()
    } catch (e: Exception) {
        e.message ?: FAILURE
    }
}
