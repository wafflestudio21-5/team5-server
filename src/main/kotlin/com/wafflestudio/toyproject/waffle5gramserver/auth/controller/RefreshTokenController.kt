package com.wafflestudio.toyproject.waffle5gramserver.auth.controller

import com.wafflestudio.toyproject.waffle5gramserver.auth.dto.RefreshTokenDto
import com.wafflestudio.toyproject.waffle5gramserver.auth.dto.RefreshTokenResponseDto
import com.wafflestudio.toyproject.waffle5gramserver.auth.service.TokenRefreshService
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class RefreshTokenController(
    private val tokenRefreshService: TokenRefreshService
) {
    @PostMapping("/api/v1/auth/refresh_token")
    fun refreshToken(
        @RequestBody refreshTokenDto: RefreshTokenDto,
        response: HttpServletResponse
    ): ResponseEntity<RefreshTokenResponseDto> {
        val refreshToken = refreshTokenDto.refreshToken
        val username = tokenRefreshService.validateRefreshToken(refreshToken)
        val newAccessToken = tokenRefreshService.generateNewAccessToken(username)
        val newRefreshToken = tokenRefreshService.generateNewRefreshToken(username)
        tokenRefreshService.addRefreshTokenCookie(response, newRefreshToken, "/api/v1/auth/refresh_token")
        return ResponseEntity<RefreshTokenResponseDto>(
            RefreshTokenResponseDto(
                username = username,
                accessToken = newAccessToken
            ),
            HttpStatus.CREATED
        )
    }
}
