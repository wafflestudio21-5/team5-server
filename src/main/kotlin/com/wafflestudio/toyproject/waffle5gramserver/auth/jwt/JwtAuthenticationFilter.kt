package com.wafflestudio.toyproject.waffle5gramserver.auth.jwt

import com.fasterxml.jackson.databind.ObjectMapper
import com.wafflestudio.toyproject.waffle5gramserver.user.service.InstagramUserService
import io.jsonwebtoken.JwtException
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.MediaType
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import java.lang.IllegalArgumentException

@Component
class JwtAuthenticationFilter(
    private val jwtUtils: JwtUtils,
    private val objectMapper: ObjectMapper,
    private val instagramUserService: InstagramUserService
) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        try {
            println("JwtAuthenticationFilter entered")
            val token = jwtUtils.getTokenFromRequest(request)
            if (token != null) {
                val username = jwtUtils.validateAccessToken(token)
                val instagramUser = instagramUserService.loadUserByUsername(username)
                SecurityContextHolder.getContext().authentication = UsernamePasswordAuthenticationToken(
                    instagramUser, null, setOf(SimpleGrantedAuthority("USER"))
                )
            } else {
                println("There is no Authorization Bearer header")
            }
            filterChain.doFilter(request, response)
        } catch (ex: Exception) {
            println("JwtAuthenticationFilter caught error with message: ${ex.message} (for the request ${request.servletPath})")
            when (ex) {
                is JwtException, is IllegalArgumentException -> {
                    respondForJwtException(response)
                }
                else -> throw ex
            }
        }
    }

    private fun respondForJwtException(response: HttpServletResponse) {
        response.status = HttpServletResponse.SC_UNAUTHORIZED
        response.contentType = MediaType.APPLICATION_JSON_VALUE
        response.writer.write(
            objectMapper.writeValueAsString(
                mapOf("message" to "Authentication token is missing or invalid.")
            )
        )
    }
}
