package com.example

import abstractcom.example.applicationservice.CommentService
import com.example.applicationservice.GroupPostService
import com.example.applicationservice.GroupService
import com.example.applicationservice.PostService
import com.example.configuration.routes
import com.example.servicechassis.*
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.context.support.beans
import org.springframework.core.env.get
import org.springframework.kafka.annotation.EnableKafka

@EnableKafka
@EnableFeignClients
@SpringBootApplication
@EnableConfigurationProperties(AuthClientProp::class)
class PostApp

fun main(args: Array<String>) {
    runApplication<PostApp>(*args) {
        beans {
            bean<CommentService>()
            bean<GroupPostService>()
            bean<GroupService>()
            bean<PostService>()
            bean {
                routes(ref(), ref(), ref(), ref())
            }
            kafkaConsumer(this)
            kafkaProducers(this, env["kafka.topics"] as List<String>)
            beanDefinitions(this)
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
    }
}
