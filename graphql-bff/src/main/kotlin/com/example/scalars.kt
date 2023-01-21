import graphql.schema.*
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeParseException

fun localDateTimeScalar(): GraphQLScalarType {
    return GraphQLScalarType.newScalar()
        .name("LocalDateTime")
        .description("Java 8 LocalDate as scalar.")
        .coercing(object : Coercing<LocalDateTime, String> {
            override fun serialize(dataFetcherResult: Any): String {
                return (dataFetcherResult as? LocalDateTime)?.toString()
                    ?: throw CoercingSerializeException("Expected a LocalDateTime object.")
            }

            override fun parseValue(input: Any): LocalDateTime {
                return try {
                    if (input is String) {
                        LocalDateTime.parse(input)
                    } else {
                        throw CoercingParseValueException("Expected a String")
                    }
                } catch (e: DateTimeParseException) {
                    throw CoercingParseValueException("Not a valid date: '$input'.", e)
                }
            }

            override fun parseLiteral(input: Any): LocalDateTime {
                return if (input is String) {
                    try {
                        LocalDateTime.parse(input)
                    } catch (e: DateTimeParseException) {
                        throw CoercingParseLiteralException(e)
                    }
                } else {
                    throw CoercingParseLiteralException("Expected a StringValue.")
                }
            }
        })
        .build()
}

fun localDateScalar(): GraphQLScalarType {
    return GraphQLScalarType.newScalar()
        .name("LocalDate")
        .description("Java 8 LocalDate as scalar.")
        .coercing(object : Coercing<LocalDate, String> {
            override fun serialize(dataFetcherResult: Any): String {
                return (dataFetcherResult as? LocalDate)?.toString()
                    ?: throw CoercingSerializeException("Expected a LocalDate object.")
            }

            override fun parseValue(input: Any): LocalDate {
                return try {
                    if (input is String) {
                        LocalDate.parse(input)
                    } else {
                        throw CoercingParseValueException("Expected a String")
                    }
                } catch (e: DateTimeParseException) {
                    throw CoercingParseValueException("Not a valid date: '$input'.", e)
                }
            }

            override fun parseLiteral(input: Any): LocalDate {
                return if (input is String) {
                    try {
                        LocalDate.parse(input)
                    } catch (e: DateTimeParseException) {
                        throw CoercingParseLiteralException(e)
                    }
                } else {
                    throw CoercingParseLiteralException("Expected a StringValue.")
                }
            }
        })
        .build()
}