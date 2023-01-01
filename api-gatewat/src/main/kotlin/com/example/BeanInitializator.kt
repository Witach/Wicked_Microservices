package com.example

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig
import io.github.resilience4j.common.circuitbreaker.configuration.CircuitBreakerConfigCustomizer
import io.github.resilience4j.timelimiter.TimeLimiterConfig
import org.springframework.cloud.circuitbreaker.resilience4j.ReactiveResilience4JCircuitBreakerFactory
import org.springframework.cloud.client.circuitbreaker.Customizer
import org.springframework.cloud.gateway.filter.factory.RetryGatewayFilterFactory
import org.springframework.cloud.gateway.route.RouteLocator
import org.springframework.cloud.gateway.route.builder.GatewayFilterSpec
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.support.GenericApplicationContext
import org.springframework.context.support.beans
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.server.SecurityWebFilterChain
import java.time.Duration

typealias UrlRestrictions = ServerHttpSecurity.AuthorizeExchangeSpec

class BeanInitializer: ApplicationContextInitializer<GenericApplicationContext> {
    override fun initialize(appContext: GenericApplicationContext) {
        return beans {
            profile("dev") {
                bean {
                    filterChain(ref(), this@BeanInitializer::devRestrictions)
                }
                bean {
                    routingDev(ref())
                }
            }
            profile("prod") {
                bean {
                    filterChain(ref(), this@BeanInitializer::prodRestriction)
                }
                bean {
                    routingProd(ref())
                }
            }
        }.initialize(appContext)
    }

    private fun filterChain(http: ServerHttpSecurity, restrictionFun: org.springframework.security.config.Customizer<UrlRestrictions>): SecurityWebFilterChain {
        return http
            .authorizeExchange(restrictionFun)
            .csrf().disable()
            .cors().disable()
            .oauth2ResourceServer { it.jwt() }
            .build()
    }

    private fun devRestrictions(http: UrlRestrictions): UrlRestrictions {
        return http.anyExchange().permitAll()
    }

    private fun prodRestriction(http: UrlRestrictions): UrlRestrictions {
        return http.pathMatchers("/actuator/**").permitAll()
            .anyExchange().authenticated()
    }

    private val circutSpec = { resielicenceConfig: GatewayFilterSpec ->
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

    private fun routingProd(builder: RouteLocatorBuilder): RouteLocator {
        return builder.routes()
            .route {
                it.path("/feed*", "/feed*", "profile/feed/*")
                    .filters(circutSpec)
                    .uri("lb://feed-app")
            }.route {
                it.path("/comment*", "/comment/*", "/group*", "/group/*", "/post*", "/post/*" )
                    .filters(circutSpec)
                    .uri("lb://post-app")
            }.route {
                it.path("/profile*", "/profile/*", "/user*", "/user/*")
                    .filters(circutSpec)
                    .uri("lb://profile-app")
            }.route {
                it.path("/auth*", "/auth*/")
                    .filters { filterspec ->
                        filterspec.rewritePath("/auth/(?<remaining>.*)",
                            "/auth/realms/SocialApp/protocol/openid-connect/\${remaining}")
                    }
                    .uri("http://key-cloak:8080/")
            }.build()
    }

    private fun routingDev(builder: RouteLocatorBuilder): RouteLocator {
        return builder.routes()
            .route {
                it.path("/feed*", "/feed*", "profile/feed/*")
                    .uri("lb://feed-app")
            }.route {
                it.path("/comment*", "/comment/*", "/group*", "/group/*", "/post*", "/post/*" )
                    .uri("lb://post-app")
            }.route {
                it.path("/profile*", "/profile/*", "/user*", "/user/*")
                    .uri("lb://profile-app")
            }.route {
                it.path("/auth*", "/auth/*")
                    .filters { filterspec ->
                        filterspec.rewritePath("/auth/(?<remaining>.*)",
                            "/auth/realms/SocialApp/protocol/openid-connect/\${remaining}")
                    }
                    .uri("http://localhost:8085/")
            }.build()
    }
}