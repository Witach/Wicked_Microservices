package com.example

import com.example.configuration.EventListeners
import com.example.configuration.Listeners
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
                bothKafka(this)
                bean {
                    ProfileService(ref(), ref(), ref(), ref())
                }
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
                profile("feign") {
                    bean {
                        routes(ref(), ref())
                    }
                }
                profile("kafka") {
                    bean<KafkaObjectMapper>()
                    bean<Listeners>()
                    bean<EventListeners>()
                    bean {
                        ImperativeSessionStorage()
                    }
                }
            }
        )
    }
}