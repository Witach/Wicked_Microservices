package com.example.servicechassis

import com.fasterxml.jackson.databind.ObjectMapper
import org.apache.kafka.clients.consumer.ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG
import org.apache.kafka.clients.consumer.ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.clients.producer.ProducerConfig.*
import org.apache.kafka.common.serialization.Serializer
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.kafka.common.serialization.StringSerializer
import org.example.DomainEvent
import org.example.EventPublisher
import org.springframework.boot.autoconfigure.kafka.DefaultKafkaProducerFactoryCustomizer
import org.springframework.context.support.BeanDefinitionDsl
import org.springframework.core.env.ConfigurableEnvironment
import org.springframework.core.env.get
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.config.KafkaListenerContainerFactory
import org.springframework.kafka.core.DefaultKafkaConsumerFactory
import org.springframework.kafka.core.DefaultKafkaProducerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer
import org.springframework.security.web.SecurityFilterChain
import org.springframework.web.servlet.function.ServerRequest
import org.springframework.web.servlet.function.paramOrNull
import java.util.*

typealias UrlRestrictions = ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry;

fun filterChain(http: HttpSecurity, restrictionFun: Customizer<UrlRestrictions>): SecurityFilterChain {
    return http
        .authorizeRequests(restrictionFun)
        .csrf().disable()
        .cors().disable()
        .oauth2ResourceServer { it.jwt() }
        .build()
}

fun devRestrictions(http: UrlRestrictions): UrlRestrictions {
    return http.anyRequest().permitAll()
}

fun prodRestriction(http: UrlRestrictions): UrlRestrictions {
    return http.antMatchers("/actuator/**").permitAll()
        .anyRequest().authenticated()
}

fun String.toUUID(): UUID {
    return UUID.fromString(this)
}

fun ServerRequest.map(s: String): UUID {
    return this.pathVariable(s).toUUID()
}

fun ServerRequest.paraMap(s: String): Int? {
    return this.paramOrNull(s)?.toInt()
}

fun beanDefinitions(dsl: BeanDefinitionDsl) {
    dsl.bean {
        AuthClientResponseErrorHandler(ref())
    }
    dsl.bean {
        AuthServerClientImpl(ref(), ref())
    }
    dsl.bean {
        SessionStorageImpl()
    }

}

fun kafkaProducers(dsl: BeanDefinitionDsl, topics: List<String>) {
    dsl.bean {
        DefaultKafkaProducerFactory<String, String>(mapOf(
                BOOTSTRAP_SERVERS_CONFIG to dsl.env["kafka.bootstrapServer"],
                KEY_SERIALIZER_CLASS_CONFIG to StringSerializer::class.java,
                VALUE_SERIALIZER_CLASS_CONFIG to Serializer<Any>{ topic, data -> ObjectMapper().writeValueAsBytes(data) }
        ))
    }
    dsl.bean<KafkaClient>()
}

fun kafkaConsumer(dsl: BeanDefinitionDsl) {
    dsl.bean {
        DefaultKafkaConsumerFactory<String, String>(mapOf(
                BOOTSTRAP_SERVERS_CONFIG to dsl.env["kafka.bootstrapServer"],
                KEY_DESERIALIZER_CLASS_CONFIG to StringDeserializer::class.java,
                VALUE_DESERIALIZER_CLASS_CONFIG to StringDeserializer::class.java
        ))
    }
    dsl.bean {
        val tmp = ConcurrentKafkaListenerContainerFactory<String, String>()
        tmp.consumerFactory = ref()
        tmp
    }
}

fun extractList(env: ConfigurableEnvironment, key: String): List<String> {
    val list = mutableListOf<String>()
    var i = 0
    while (env["$key[$i]"] != null) {
        list.add(env["$key[$i]"]!!)
        i++
    }
    return list
}