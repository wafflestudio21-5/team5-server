package com.wafflestudio.toyproject.waffle5gramserver.dev

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
// https://stackoverflow.com/questions/66018084/how-to-enable-spring-security-kotlin-dsl#comment135251430_66042992
import org.springframework.security.config.annotation.web.invoke
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableWebSecurity
@Profile("dev")
class DevSecurityConfig {
    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http {
            csrf {
                disable()
            }
            headers {
                frameOptions {
                    disable()
                }
            }
            authorizeHttpRequests {
                authorize(anyRequest, permitAll)
            }
            formLogin { }
            httpBasic { }
        }
        return http.build()
    }
}
