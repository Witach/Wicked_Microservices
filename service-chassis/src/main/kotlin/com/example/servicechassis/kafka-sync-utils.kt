package com.example.servicechassis

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


data class MessageSync(var body: Any, var param: Map<String, Any>, var pathVariable: Map<String, Any>)
data class MessageSync2<T>(var body: T)



class KafkaObjectMapper(val objectMapper: ObjectMapper) {

    fun < T: Any> readBody(consumerRecord: String, klass: Class<T>): T {
        val str = objectMapper.readTree(consumerRecord)[BODY_KEY].toString()
        return objectMapper.readValue(str, klass)
    }

    fun <T: Any> readParameter(consumerRecord: String, param: String, klass: Class<T>): T? {
        val firstMap = objectMapper.readValue(consumerRecord, Map::class.java)
        firstMap[PARAM_KEY] ?: return null
        val secondMap = objectMapper.readValue(firstMap[PARAM_KEY] as String, Map::class.java)[param]
        return objectMapper.readValue(secondMap as String, klass)
    }


    fun readPathVariable(consumerRecord: String, param: String): UUID {
        val firstMap = objectMapper.readTree(consumerRecord)[PATH_VARIABLE_KEY].toString()
        val secondMap = objectMapper.readValue(firstMap, Map::class.java)[param]
        return UUID.fromString(secondMap as String)
    }


    fun <T> readParamList(consumerRecord: String, param: String, klass: Class<T>): List<T> {
        val firstMap = objectMapper.readTree(consumerRecord)[PARAM_KEY].toString()
        val secondMap = objectMapper.readValue(firstMap, Map::class.java)[param] as String
        return secondMap
            .split(",")
            .map { it.trim() }
            .map { objectMapper.readValue(it, klass) }
            .toList()
    }

    fun convertToMessageFromBody(body: String): String {
        return objectMapper.writeValueAsString(
            mapOf(
                BODY_KEY to body
            )
        )
    }

    fun convertToMessageFromBodyObject(body: Any): String {
        return objectMapper.writeValueAsString(
            mapOf(
                BODY_KEY to body
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
                PATH_VARIABLE_KEY to messageSync.pathVariable
            )
        )
    }

    fun convertToMessageFromPathVariable(pathvariable: Pair<String, Any>): String {
        return objectMapper.writeValueAsString(
            mapOf(
                PATH_VARIABLE_KEY to mapOf(pathvariable)
            )
        )
    }

    fun consumerRecordToMap(record: ConsumerRecord<String, String>): Map<String, String> {
        return objectMapper.readValue(record.value(), Map::class.java) as Map<String, String>
    }

}
