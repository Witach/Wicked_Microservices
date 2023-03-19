package com.example

import com.example.configuration.EventListeners
import com.example.configuration.Listeners
import com.example.configuration.routes
import com.example.service.LogginginCommand
import com.example.service.ProfileService
import com.example.service.UserService
import com.example.servicechassis.*
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.context.support.beans
import org.springframework.web.servlet.function.router

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
                bean<LogginginCommand>()
                profile("dev & feign") {
                    bean<EventPublisherMock>()
                    bean {

                    }
                }
                profile("grpc") {
                    bean {
                        GRPCSessionStorage()
                    }
                }
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
                        routes(ref(), ref()).and(
                            router {
                                GET("/events") { req ->
                                    val events = ref<EventPublisherMock>().listMap
                                    ok().body(events)
                                }
                            }
                        )
                    }
                }
                profile("grpc") {
                    bean<EventPublisherMock>()
                }
                profile("kafka") {
                    bean<KafkaObjectMapper>()
                    bean<EventListeners>()
                    bean {
                        ImperativeSessionStorage()
                    }
                    bean<Listeners>()
                    bean {
                        KafkaAnswerTemplate(ref(), ref())
                    }
                }
            }
        )
    }
}