package com.wafflestudio.toyproject.waffle5gramserver.auth

import com.wafflestudio.toyproject.waffle5gramserver.auth.jwt.JwtAuthenticationFilter
import com.wafflestudio.toyproject.waffle5gramserver.auth.oauth2.CustomOAuth2FailureHandler
import com.wafflestudio.toyproject.waffle5gramserver.auth.oauth2.CustomOAuth2SuccessHandler
import com.wafflestudio.toyproject.waffle5gramserver.auth.oauth2.CustomOAuth2UserService
import jakarta.servlet.DispatcherType
import org.springframework.boot.autoconfigure.security.servlet.PathRequest
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.ProviderManager
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
// https://stackoverflow.com/questions/66018084/how-to-enable-spring-security-kotlin-dsl#comment135251430_66042992
import org.springframework.security.config.annotation.web.invoke
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.factory.PasswordEncoderFactories
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.access.intercept.AuthorizationFilter
import org.springframework.security.web.util.matcher.DispatcherTypeRequestMatcher
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

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
            formLogin { disable() }
            httpBasic { disable() }
            logout { disable() }
        }
        return http.build()
    }

    @Profile("dev-secure")
    @Bean
    fun devSecureFilterChain(
        http: HttpSecurity,
        jwtAuthenticationFilter: JwtAuthenticationFilter,
        corsConfigurationSource: CorsConfigurationSource,
        customOAuth2UserService: CustomOAuth2UserService,
        customOAuth2SuccessHandler: CustomOAuth2SuccessHandler,
        customOAuth2FailureHandler: CustomOAuth2FailureHandler
    ): SecurityFilterChain {

        val authorizedHttpMethod = listOf(
            HttpMethod.GET, HttpMethod.POST, HttpMethod.PUT, HttpMethod.PATCH, HttpMethod.DELETE
        )
        http.addFilterBefore(jwtAuthenticationFilter, AuthorizationFilter::class.java)
        http {
            sessionManagement {
                // Don't store in-memory session (JSESSIONID cookie)
                // Enable NullSecurityContextRepository and NullRequestCache
                sessionCreationPolicy = SessionCreationPolicy.STATELESS
            }
            authorizeHttpRequests {
                authorize(PathRequest.toH2Console(), permitAll)
                authorize("/api/v1/auth/**", permitAll)
                authorize(DispatcherTypeRequestMatcher(DispatcherType.FORWARD), permitAll)
                authorize(DispatcherTypeRequestMatcher(DispatcherType.ERROR), permitAll)
                authorizedHttpMethod.forEach {
                    authorize(it, "/api/v1/**", hasAuthority("USER"))
                }
                authorize(anyRequest, denyAll)
            }
            cors {
                configurationSource = corsConfigurationSource
            }
            csrf { disable() }
            headers { frameOptions { disable() } }
            formLogin { disable() }
            httpBasic { disable() }
            logout { disable() }
            oauth2Login {
                userInfoEndpoint {
                    userService = customOAuth2UserService
                }
                authenticationSuccessHandler = customOAuth2SuccessHandler
                authenticationFailureHandler = customOAuth2FailureHandler
            }
        }
        return http.build()
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder()
    }

    @Bean
    fun authenticationManager(
        userDetailsService: UserDetailsService,
        passwordEncoder: PasswordEncoder
    ): AuthenticationManager {
        val authenticationProvider = DaoAuthenticationProvider()
        authenticationProvider.setUserDetailsService(userDetailsService)
        authenticationProvider.setPasswordEncoder(passwordEncoder)
        return ProviderManager(authenticationProvider)
    }

    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource {
        val configuration = CorsConfiguration()
        configuration.allowedOrigins = listOf(
            "https://www.waffle5gram.com"
        )
        configuration.allowedMethods = listOf("*")
        configuration.allowedHeaders = listOf("*")
        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", configuration)
        return source
    }
}
