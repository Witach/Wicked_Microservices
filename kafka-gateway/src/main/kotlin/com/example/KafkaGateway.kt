package com.example

import com.example.servicechassis.*
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.context.support.beans
import org.springframework.kafka.annotation.EnableKafka
import org.springframework.web.servlet.function.*
import java.util.*


@EnableKafka
@EnableFeignClients
@SpringBootApplication
@EnableConfigurationProperties(AuthClientProp::class)
class KafkaGateway

fun main(args: Array<String>) {
    runApplication<KafkaGateway>(*args) {
        addInitializers(
            beans {
                profile("kafka") {
                    bean {
                        SessionStorageImpl()
                    }
                }
                bean<KafkaObjectMapper>()
                beanDefinitions(this)
                kafkaProducers(this)
                bean {
                    filterChain(ref(), ::devRestrictions)
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
