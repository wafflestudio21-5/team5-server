package com.wafflestudio.toyproject.waffle5gramserver.auth.service

import com.wafflestudio.toyproject.waffle5gramserver.auth.jwt.JwtUtils
import com.wafflestudio.toyproject.waffle5gramserver.global.error_handling.BusinessException
import com.wafflestudio.toyproject.waffle5gramserver.global.error_handling.ErrorCode
import com.wafflestudio.toyproject.waffle5gramserver.properties.JWTProperties
import io.jsonwebtoken.JwtException
import jakarta.servlet.http.Cookie
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.stereotype.Service

@Service
class TokenRefreshServiceImpl(
    private val jwtUtils: JwtUtils,
    private val jwtProperties: JWTProperties
) : TokenRefreshService {
    override fun extractRefreshToken(request: HttpServletRequest): String {
        println("request.cookies == null : ${request.cookies == null}")
        for (h in request.headerNames) {
            println("header : $h")
        }
        val cookies = request.cookies
            ?: throw BusinessException(ErrorCode.REFRESH_TOKEN_NOT_FOUND)
        for (c in request.cookies) {
            println("cookie.name: ${c.name} / cookie.value: ${c.value}")
        }
        val refreshTokenCookie = cookies.firstOrNull { it.name == "refresh_token" }
            ?: throw BusinessException(ErrorCode.REFRESH_TOKEN_NOT_FOUND)
        return refreshTokenCookie.value
    }

    override fun validateRefreshToken(token: String): String {
        try {
            return jwtUtils.validateRefreshToken(token)
        } catch (ex: JwtException) {
            throw BusinessException(ErrorCode.REFRESH_TOKEN_INVALID)
        }
    }

    override fun generateNewAccessToken(username: String): String {
        try {
            return jwtUtils.generateAccessToken(username)
        } catch (ex: JwtException) {
            throw BusinessException(ErrorCode.TOKEN_GENERATION_FAIL)
        }
    }

    override fun generateNewRefreshToken(username: String): String {
        try {
            return jwtUtils.generateRefreshToken(username)
        } catch (ex: JwtException) {
            throw BusinessException(ErrorCode.TOKEN_GENERATION_FAIL)
        }
    }

    override fun addRefreshTokenCookie(
        response: HttpServletResponse,
        token: String,
        cookiePath: String
    ) {
        val refreshTokenCookie = Cookie("refresh_token", token).apply {
            path = cookiePath
            isHttpOnly = false
            secure = jwtProperties.refreshTokenCookieSecure
            maxAge = jwtProperties.ttlMinutesRefreshToken.toInt() * 60 // minutes to seconds
            setAttribute("SameSite", "None")
            domain = "https://waffle5gram.shop"
        }
        response.addCookie(refreshTokenCookie)
    }
}
