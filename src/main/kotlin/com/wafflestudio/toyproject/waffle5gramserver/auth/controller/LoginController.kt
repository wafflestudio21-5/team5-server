package com.wafflestudio.toyproject.waffle5gramserver.auth.controller

import com.wafflestudio.toyproject.waffle5gramserver.auth.dto.AccessTokenResponseDto
import com.wafflestudio.toyproject.waffle5gramserver.auth.dto.LoginRequestDto
import com.wafflestudio.toyproject.waffle5gramserver.auth.service.TokenRefreshService
import com.wafflestudio.toyproject.waffle5gramserver.auth.service.UserAuthService
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.AuthenticationException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class LoginController(
    private val userAuthService: UserAuthService,
    private val tokenRefreshService: TokenRefreshService
) {

    @PostMapping("/api/v1/auth/login")
    fun login(
        @RequestBody loginRequest: LoginRequestDto,
        response: HttpServletResponse
    ): ResponseEntity<AccessTokenResponseDto> {
        userAuthService.authenticateUsernamePassword(loginRequest.username, loginRequest.password)
        val accessToken = tokenRefreshService.generateNewAccessToken(loginRequest.username)
        val refreshToken = tokenRefreshService.generateNewRefreshToken(loginRequest.username)
        tokenRefreshService.addRefreshTokenCookie(response, refreshToken, "/api/v1/auth/refresh_token")
        return ResponseEntity<AccessTokenResponseDto>(AccessTokenResponseDto(accessToken), HttpStatus.CREATED)
    }

    @ExceptionHandler(AuthenticationException::class)
    fun handleLoginAuthenticationException(ex: AuthenticationException): ResponseEntity<Any> {
        return ResponseEntity(mapOf("error" to "User not found"), HttpStatus.NOT_FOUND)
    }
}
