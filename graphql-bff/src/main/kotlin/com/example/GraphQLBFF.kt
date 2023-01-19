package com.example

import com.example.servicechassis.*
import graphql.scalars.ExtendedScalars
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.context.support.BeanDefinitionDsl
import org.springframework.context.support.beans
import org.springframework.graphql.execution.RuntimeWiringConfigurer
import org.springframework.web.servlet.function.*
import java.util.*


@EnableFeignClients
@SpringBootApplication
@EnableConfigurationProperties(AuthClientProp::class)
class GraphQLBFF

fun main(args: Array<String>) {
    runApplication<GraphQLBFF>(*args) {
        addInitializers(
            beans {
                beanDefinitions(this)
                beansInit(this)
            }

        )
    }
}


fun beansInit(dsl: BeanDefinitionDsl) {
    dsl.bean() {
        routes(dsl)
    }
}

fun graphQlBeans(dsl: BeanDefinitionDsl) {
    dsl.bean {
        RuntimeWiringConfigurer {
            it.scalar(ExtendedScalars.UUID)
            it.scalar(ExtendedScalars.DateTime)
        }
    }
}

fun routes(dsl: BeanDefinitionDsl): RouterFunction<ServerResponse> {
    return router {

    }
}

