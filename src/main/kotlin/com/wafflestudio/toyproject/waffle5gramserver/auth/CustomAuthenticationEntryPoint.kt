package com.wafflestudio.toyproject.waffle5gramserver.auth

import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.MediaType
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component

@Component
class CustomAuthenticationEntryPoint(
    private val objectMapper: ObjectMapper
) : AuthenticationEntryPoint {
    override fun commence(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        authException: AuthenticationException?
    ) {
        println("CustomAuthenticationEntryPoint : ${authException?.message} (for the request ${request?.servletPath})")
        // authException?.printStackTrace()
        response?.let {
            it.contentType = MediaType.APPLICATION_JSON_VALUE
            it.status = HttpServletResponse.SC_UNAUTHORIZED
            it.writer.write(
                objectMapper.writeValueAsString(
                    mapOf("error" to "Authentication Exception from Custom Authentication Entry Point")
                )
            )
        }
    }
}
