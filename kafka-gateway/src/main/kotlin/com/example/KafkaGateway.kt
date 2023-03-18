package com.example

import com.example.servicechassis.*
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.context.support.beans
import org.springframework.jdbc.datasource.DataSourceTransactionManager
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType
import org.springframework.kafka.annotation.EnableKafka
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.session.jdbc.config.annotation.web.http.EnableJdbcHttpSession
import org.springframework.web.servlet.function.*
import java.util.*


@EnableKafka
@EnableFeignClients
@SpringBootApplication
@EnableJdbcHttpSession
@EnableConfigurationProperties(AuthClientProp::class)
class KafkaGateway

fun main(args: Array<String>) {
    runApplication<KafkaGateway>(*args) {
        addInitializers(
            beans {
                bean {
                    EmbeddedDatabaseBuilder()
                        .setType(EmbeddedDatabaseType.H2)
                        .addScript("classpath:org/springframework/session/jdbc/schema-h2.sql")
                        .build()
                }
                bean {
                    DataSourceTransactionManager(ref())
                }
                profile("kafka") {
                    bean {
                        SessionStorageImpl()
                    }
                }
                bean<KafkaObjectMapper>()
                beanDefinitions(this)
                kafkaProducers(this)
                bean {
                    ref<HttpSecurity>().authorizeRequests().anyRequest().permitAll().and()
                        .csrf().disable()
                        .cors().disable()
                        .oauth2ResourceServer { it.jwt() }
                        .csrf().disable()
                        .headers().frameOptions().disable().and()
                        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.ALWAYS).and()
                        .build()
                }
                bean {
                    groupRoutes(ref(), ref())
                        .and(commentRoutes(ref(), ref()))
                        .and(groupPostRoutes(ref(), ref()))
                        .and(postRoutes(ref(), ref()))
                        .and(profileRoutes(ref(), ref()))
                        .and(feedRoutes(ref(), ref()))
                }
                bean {
                    router {
                        path("/feed").nest {
                            GET("") {
                                kafkaProxy {
                                    requestTopic = "feed-get-request"
                                    kafkaTemplate = ref()
                                    post = ref<KafkaObjectMapper>().convertToMessageFrom {
                                        param = mapOf(
                                            "profiles" to it.param("profiles").orElse(""),
                                            "groups" to it.param("groups").orElse("")
                                        )
                                    }
                                }()
                            }
                        }
                    }
                }
            }
        )
    }
}
