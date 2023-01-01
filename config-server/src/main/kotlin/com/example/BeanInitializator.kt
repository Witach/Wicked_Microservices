package com.example

import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.support.GenericApplicationContext
import org.springframework.context.support.beans
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.web.SecurityFilterChain

class BeanInitializer: ApplicationContextInitializer<GenericApplicationContext> {
    override fun initialize(appContext: GenericApplicationContext) {
        return beans {
            bean {
                filterChain(ref())
            }
        }.initialize(appContext)
    }

    private fun filterChain(http: HttpSecurity): SecurityFilterChain {
        return http
            .authorizeHttpRequests { it.anyRequest().permitAll() }
            .csrf().disable()
            .cors().disable()
            .build()
    }
}