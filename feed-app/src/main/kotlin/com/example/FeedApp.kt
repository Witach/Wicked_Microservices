package com.example

import com.example.configuration.Listeners
import com.example.configuration.routes
import com.example.servicechassis.*
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.context.support.beans

@EnableFeignClients
@SpringBootApplication
@EnableConfigurationProperties(AuthClientProp::class)
class FeedApp

fun main(args: Array<String>) {
    runApplication<FeedApp>(*args) {
        addInitializers (
            beans {
                profile("grpc") {
                    bean {
                        SessionStorageImpl()
                    }
                }
                beanDefinitions(this)
                profile("feign") {
                    bean {
                        routes(ref(), ref())
                    }
                }
                profile("grpc") {
                    bean {
                        routes(ref(), ref())
                    }
                }
                profile("dev") {
                    bean {
                        filterChain(ref(), ::devRestrictions)
                    }
                }
                profile("prod") {
                    bean {
                        filterChain(ref(), ::prodRestriction)
                    }
                }
                profile("kafka") {
                    bean<KafkaObjectMapper>()
                    kafkaReplyingProducers(this)
                    bean<Listeners>()
                    bean {
                        ImperativeSessionStorage()
                    }
                    bean<KafkaAnswerTemplate>()
                }
            }
        )
    }
}