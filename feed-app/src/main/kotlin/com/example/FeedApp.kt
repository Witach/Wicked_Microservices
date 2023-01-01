package com.example

import com.example.configuration.routes
import com.example.domainservice.FeedService
import com.example.servicechassis.*
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.context.support.beans
import org.springframework.core.env.get

@EnableFeignClients
@SpringBootApplication
class FeedApp

fun main(args: Array<String>) {
    runApplication<FeedApp>(*args) {
        addInitializers (
            beans {
                bean {
                    FeedService(
                        env["feed-app.feed.size"]?.toInt() ?: 20,
                        ref(), ref(), ref())
                }
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
