package com.wafflestudio.toyproject.waffle5gramserver.auth.service

import jakarta.servlet.http.HttpServletRequest

interface TokenRefreshService {
    fun extractRefreshToken(request: HttpServletRequest): String
    fun validateRefreshToken(token: String): String
    fun generateNewAccessToken(username: String): String
    fun generateNewRefreshToken(username: String): String
}