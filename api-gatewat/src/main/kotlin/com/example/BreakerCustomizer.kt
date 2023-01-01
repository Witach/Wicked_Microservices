package com.example

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig
import io.github.resilience4j.timelimiter.TimeLimiterConfig
import org.springframework.cloud.circuitbreaker.resilience4j.ReactiveResilience4JCircuitBreakerFactory
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder
import org.springframework.cloud.client.circuitbreaker.Customizer
import org.springframework.stereotype.Component
import java.time.Duration

@Component
class BreakerCustomize: Customizer<ReactiveResilience4JCircuitBreakerFactory> {
    override fun customize(tocustomize: ReactiveResilience4JCircuitBreakerFactory?) {
        tocustomize?.configureDefault { default ->
            Resilience4JConfigBuilder(default)
                .circuitBreakerConfig(
                    CircuitBreakerConfig.custom()
                        .slidingWindowSize(20)
                        .permittedNumberOfCallsInHalfOpenState(5)
                        .failureRateThreshold(50F)
                        .waitDurationInOpenState(Duration.ofSeconds(60))
                        .build())
                .timeLimiterConfig(
                    TimeLimiterConfig.custom()
                        .timeoutDuration(Duration.ofMillis(500))
                        .build())
                .build()
        }
    }
}