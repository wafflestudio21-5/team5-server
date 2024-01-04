package com.wafflestudio.toyproject.waffle5gramserver.auth

import jakarta.servlet.DispatcherType
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
// https://stackoverflow.com/questions/66018084/how-to-enable-spring-security-kotlin-dsl#comment135251430_66042992
import org.springframework.security.config.annotation.web.invoke
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.factory.PasswordEncoderFactories
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.util.matcher.DispatcherTypeRequestMatcher

@Configuration
@EnableWebSecurity
class SecurityConfig {

    @Profile("dev")
    @Bean
    fun devFilterChain(http: HttpSecurity): SecurityFilterChain {
        http {
            csrf {
                disable()
            }
            headers {
                frameOptions {
                    disable()
                }
            }
            sessionManagement {
                sessionCreationPolicy = SessionCreationPolicy.STATELESS
            }
            authorizeHttpRequests {
                authorize(anyRequest, permitAll)
            }
            formLogin { }
            httpBasic { }
        }
        return http.build()
    }

    @Profile("prod")
    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        val authorizedHttpMethod = listOf(
            HttpMethod.POST, HttpMethod.PUT, HttpMethod.PATCH, HttpMethod.DELETE
        )
        http {
            sessionManagement {
                // Don't store in-memory session (JSESSIONID cookie)
                // Enable NullSecurityContextRepository and NullRequestCache
                sessionCreationPolicy = SessionCreationPolicy.STATELESS
            }
            authorizeHttpRequests {
                authorize("/api/v1/auth/**", permitAll)
                authorize(DispatcherTypeRequestMatcher(DispatcherType.FORWARD), permitAll)
                authorize(DispatcherTypeRequestMatcher(DispatcherType.ERROR), permitAll)
                authorizedHttpMethod.forEach {
                    authorize(it, "/api/v1/**", hasAuthority("USER"))
                }
                authorize(HttpMethod.GET, "/api/v1/**", permitAll)
                authorize(anyRequest, denyAll)
            }
            formLogin { disable() }
            httpBasic { disable() }
        }
        return http.build()
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder()
    }
}