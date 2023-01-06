package com.example

import com.example.configuration.routes
import com.example.service.ProfileService
import com.example.service.UserService
import com.example.servicechassis.*
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.context.support.beans

@EnableFeignClients
@SpringBootApplication
@EnableConfigurationProperties(AuthClientProp::class)
class ProfileApp

fun main(args: Array<String>) {
    runApplication<ProfileApp>(*args) {
        addInitializers(
            beans {
                beanDefinitions(this)
                kafkaConsumer(this)
                kafkaProducers(this, env.activeProfiles)
                bean<ProfileService>()
                bean<UserService>()
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
                bean {
                    routes(ref(), ref())
                }

            }
        )
    }
}