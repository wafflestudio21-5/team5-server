package com.wafflestudio.toyproject.waffle5gramserver.auth.controller

import com.wafflestudio.toyproject.waffle5gramserver.auth.dto.AccessTokenResponseDto
import com.wafflestudio.toyproject.waffle5gramserver.auth.service.TokenRefreshService
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class RefreshTokenController(
    private val tokenRefreshService: TokenRefreshService
) {
    @GetMapping("/api/v1/auth/refresh_token")
    fun refreshToken(
        request: HttpServletRequest,
        response: HttpServletResponse
    ): ResponseEntity<AccessTokenResponseDto> {
        val refreshToken = tokenRefreshService.extractRefreshToken(request)
        val username = tokenRefreshService.validateRefreshToken(refreshToken)
        val newAccessToken = tokenRefreshService.generateNewAccessToken(username)
        val newRefreshToken = tokenRefreshService.generateNewRefreshToken(username)
        tokenRefreshService.addRefreshTokenCookie(response, newRefreshToken, "/api/v1/auth/refresh_token")
        return ResponseEntity<AccessTokenResponseDto>(
            AccessTokenResponseDto(accessToken = newAccessToken),
            HttpStatus.CREATED
        )
    }
}
