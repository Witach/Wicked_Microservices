package com.example

import abstractcom.example.applicationservice.CommentService
import com.example.applicationservice.GroupPostService
import com.example.applicationservice.GroupService
import com.example.applicationservice.PostService
import com.example.applicationservice.SessionStorage
import com.example.configuration.routes
import com.example.entity.Group
import com.example.service.CommentListener
import com.example.service.GroupListener
import com.example.service.GroupPostListener
import com.example.service.PostListener
import com.example.servicechassis.*
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.context.support.beans
import org.springframework.kafka.annotation.EnableKafka
import java.util.function.Predicate

@EnableKafka
@EnableFeignClients
@SpringBootApplication
@EnableConfigurationProperties(AuthClientProp::class)
class PostApp

fun main(args: Array<String>) {
    runApplication<PostApp>(*args) {
        addInitializers(
            beans {
                kafkaProducers(this)
                bean<CommentService>()
                bean<PostService>()
                bean {
                    routes(ref(), ref(), ref(), ref())
                }
                beanDefinitions(this)
                profile("dev") {
                    bean {
                        filterChain(ref(), ::devRestrictions)
                    }
                    bean {
                        GroupService(ref(), ref(), ref(), { true }, ref())
                    }
                    bean {
                        GroupPostService(ref(), ref(), ref(), ref(), ref()) { true }
                    }
                }
                profile("prod") {

                    bean {
                        filterChain(ref(), ::prodRestriction)
                    }
                    bean {
                        val x = Predicate<Group> {
                            it.isAdministrator(ref<SessionStorage>().sessionOwner.userId!!)
                        }
                        GroupService(ref(), ref(), ref(), x, ref())
                    }
                    bean {
                        GroupPostService(ref(), ref(), ref(), ref(), ref()) {
                            it.profiles.contains(ref<SessionStorage>().sessionOwner.userId!!)
                        }
                    }
                }
                profile("kafka") {
                    bean<CommentListener>()
                    bean<GroupPostListener>()
                    bean<GroupListener>()
                    bean<PostListener>()
                    bean<KafkaObjectMapper>()
                }
            }
        )
    }
}