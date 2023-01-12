package com.example.servicechassis

import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.consumer.ConsumerConfig.*
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.clients.producer.ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG
import org.apache.kafka.clients.producer.ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.kafka.common.serialization.StringSerializer
import org.springframework.context.support.BeanDefinitionDsl
import org.springframework.core.env.ConfigurableEnvironment
import org.springframework.core.env.get
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.core.DefaultKafkaConsumerFactory
import org.springframework.kafka.core.DefaultKafkaProducerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.listener.ContainerProperties
import org.springframework.kafka.listener.KafkaMessageListenerContainer
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer
import org.springframework.security.web.SecurityFilterChain
import org.springframework.web.servlet.function.ServerRequest
import org.springframework.web.servlet.function.paramOrNull
import org.springframework.web.servlet.function.router
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
    dsl.profile("prod") {
        dsl.bean {
            SessionStorageImpl()
        }
    }
    dsl.profile("dev") {
        dsl.bean {
            SessionStorageMock()
        }
    }

}

fun kafkaProducers(dsl: BeanDefinitionDsl) {
    dsl.profile("!kafka") {
        dsl.bean {
            dsl.bean<EventPublisherMock>()
        }
        dsl.bean {
            router {
                GET("/events") { req ->
                    val events = ref<EventPublisherMock>().listMap
                    ok().body(events)
                }
            }
        }
    }
    dsl.profile("kafka") {
        dsl.bean<KafkaClient>()
        dsl.bean {
            ReplyingKafkaTemplate<String, String, String>(ref(), ref())
        }
        dsl.bean {
            KafkaMessageListenerContainer<String, String>(ref(), ContainerProperties(*extractList(dsl.env,
                "kafka.topics").toTypedArray()))
        }
        dsl.bean {
            DefaultKafkaProducerFactory<String, String>(ref())
        }
        dsl.bean {
            ProducerConfig(
                mapOf(
                    ProducerConfig.BOOTSTRAP_SERVERS_CONFIG to dsl.env["kafka.bootstrapServer"],
                    KEY_SERIALIZER_CLASS_CONFIG to StringSerializer::class.java,
                    VALUE_SERIALIZER_CLASS_CONFIG to StringSerializer::class.java
                )
            )
        }
        dsl.bean {
            DefaultKafkaConsumerFactory<String, String>(ref())
        }
        dsl.bean {
            ConsumerConfig(
                mapOf(
                    ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG to dsl.env["kafka.bootstrapServer"],
                    KEY_DESERIALIZER_CLASS_CONFIG to StringDeserializer::class.java,
                    VALUE_DESERIALIZER_CLASS_CONFIG to StringDeserializer::class.java,
                    GROUP_ID_CONFIG to dsl.env["spring.application.name"],
                )
            )
        }
        dsl.bean {
            ConcurrentKafkaListenerContainerFactory<String, String>().apply {
                consumerFactory = ref()
                setReplyTemplate(ref())
            }
        }
        dsl.bean {
            KafkaTemplate<String, String>(ref())
        }
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