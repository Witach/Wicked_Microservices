//package com.example
//
//import graphql.schema.Coercing
//import graphql.schema.GraphQLScalarType
//import java.time.LocalDateTime
//import java.time.format.DateTimeFormatter
//
//const val LocalDateTimeScalar = GraphQLScalarType.newScalar()
//    .name("LocalDateTime")
//    .description("Date projection")
//    .coercing()
//
//class CoercingLocalDateTime: Coercing<String, LocalDateTime> {
//    override fun serialize(dataFetcherResult: Any): LocalDateTime {
//
//    }
//
//    override fun parseValue(input: Any): String {
//        return LocalDateTime.from(DateTimeFormatter.ISO_DATE_TIME.parse(input))
//    }
//
//    override fun parseLiteral(input: Any): String {
//
//    }
//    //    fun serialize(dataFetcherResult: Object?): LocalDateTime? {
////        return LocalDateTime.from(DateTimeFormatter.ISO_DATE_TIME.parse(dataFetcherResult))
////    }
////
////    fun parseValue(input: String?): LocalDateTime? {
////
////    }
////
////    fun parseLiteral(input: Any?): Any? {
////        return input.toString()
////    }
//}