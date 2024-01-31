package com.wafflestudio.toyproject.waffle5gramserver.auth.service

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse

interface TokenRefreshService {
    fun extractRefreshTokenFromCookie(request: HttpServletRequest): String
    fun validateRefreshToken(token: String): String
    fun generateNewAccessToken(username: String): String
    fun generateNewRefreshToken(username: String): String
    fun addRefreshTokenCookie(response: HttpServletResponse, token: String, cookiePath: String)
}
