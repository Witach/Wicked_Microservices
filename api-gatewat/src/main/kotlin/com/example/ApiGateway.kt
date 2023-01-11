package com.example

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.gateway.filter.factory.RetryGatewayFilterFactory
import org.springframework.cloud.gateway.route.RouteLocator
import org.springframework.cloud.gateway.route.builder.GatewayFilterSpec
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder
import org.springframework.context.annotation.Configuration
import org.springframework.context.support.GenericApplicationContext
import org.springframework.context.support.beans
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.web.server.SecurityWebFilterChain
import java.time.Duration

@SpringBootApplication
class ApiGateway

typealias UrlRestrictions = ServerHttpSecurity.AuthorizeExchangeSpec
fun main(args: Array<String>) {
    runApplication<ApiGateway>(*args) {
        addInitializers(
            beans {
                profile("dev") {
                    bean {
                        filterChain(ref(), ::devRestrictions)
                    }
                    bean {
                        routingDev(ref())
                    }
                }
                profile("prod") {
                    bean {
                        filterChain(ref(), ::prodRestriction)
                    }
                    bean {
                        routingProd(ref())
                    }
                }
            }
        )
    }
}

fun filterChain(http: ServerHttpSecurity, restrictionFun: org.springframework.security.config.Customizer<UrlRestrictions>): SecurityWebFilterChain {
    return http
        .authorizeExchange(restrictionFun)
        .csrf().disable()
        .cors().disable()
        .oauth2ResourceServer { it.jwt() }
        .build()
}

fun devRestrictions(http: UrlRestrictions): UrlRestrictions {
    return http.anyExchange().permitAll()
}

fun prodRestriction(http: UrlRestrictions): UrlRestrictions {
    return http.pathMatchers("/actuator/**").permitAll()
        .anyExchange().authenticated()
}

val circutSpec = { resielicenceConfig: GatewayFilterSpec ->
    resielicenceConfig.retry { retryConfig ->
        retryConfig.retries = 3
        retryConfig.setBackoff(
            RetryGatewayFilterFactory.BackoffConfig(
                Duration.ofMillis(50),
                Duration.ofMillis(500),
                2,
                true
            )
        )
        retryConfig.allMethods()
    }
}

fun routingProd(builder: RouteLocatorBuilder): RouteLocator {
    return builder.routes()
        .route {
            it.path("/feed**", "/feed/**", "profile/feed/**")
                .filters(circutSpec)
                .uri("lb://feed-app")
        }.route {
            it.path("/comment**", "/comment/**", "/group**", "/group/**", "/post**", "/post/**" )
                .filters(circutSpec)
                .uri("lb://post-app")
        }.route {
            it.path("/profile**", "/profile/**", "/user**", "/user/**")
                .filters(circutSpec)
                .uri("lb://profile-app")
        }.route {
            it.path("/auth**", "/auth**/")
                .filters { filterspec ->
                    filterspec.rewritePath("/auth/(?<remaining>.*)",
                        "/auth/realms/SocialApp/protocol/openid-connect/\${remaining}")
                }
                .uri("http://key-cloak:8080/")
        }.build()
}

fun routingDev(builder: RouteLocatorBuilder): RouteLocator {
    return builder.routes()
        .route {
            it.path("/user/**", "/user**")
                .uri("lb://profile-app")
        }
        .route {
            it.path("/feed**", "/feed/**", "profile/feed/**")
                .uri("lb://feed-app")
        }.route {
            it.path("/comment**", "/comment/**", "/group**", "/group/**", "/post**", "/post/**" )
                .uri("lb://post-app")
        }.route {
            it.path("/profile**", "/profile/**", "/user**", "/user/**")
                .uri("lb://profile-app")
        }.route {
            it.path("/auth**", "/auth/**")
                .filters { filterspec ->
                    filterspec.rewritePath("/auth/(?<remaining>.*)",
                        "/auth/realms/SocialApp/protocol/openid-connect/\${remaining}")
                }
                .uri("http://localhost:8085/")
        }.build()
}