package com.example

import com.example.configuration.routes
import com.example.servicechassis.*
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.context.support.beans
import org.springframework.core.env.get

@EnableFeignClients
@SpringBootApplication
@EnableConfigurationProperties(AuthClientProp::class)
class FeedApp

fun main(args: Array<String>) {
    runApplication<FeedApp>(*args) {
        addInitializers (
            beans {
                beanDefinitions(this)
                bean {
                    routes(ref(), ref(), ref())
                }
                kafkaConsumer(this)
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
            }
        )
    }
}