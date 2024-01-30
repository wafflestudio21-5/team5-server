package com.wafflestudio.toyproject.waffle5gramserver.auth

import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.MediaType
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.web.access.AccessDeniedHandler
import org.springframework.stereotype.Component

@Component
class CustomAccessDeniedHandler(
    private val objectMapper: ObjectMapper
) : AccessDeniedHandler {
    override fun handle(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        accessDeniedException: AccessDeniedException?
    ) {
        println("CustomAuthenticationEntryPoint : ${accessDeniedException?.message} (for the request ${request?.servletPath})")
        // accessDeniedException?.printStackTrace()
        response?.let {
            it.contentType = MediaType.APPLICATION_JSON_VALUE
            it.status = HttpServletResponse.SC_UNAUTHORIZED
            it.writer.write(
                objectMapper.writeValueAsString(
                    mapOf("error" to "Access Denied Exception from Custom Access Denied Handler")
                )
            )
        }
    }
}
