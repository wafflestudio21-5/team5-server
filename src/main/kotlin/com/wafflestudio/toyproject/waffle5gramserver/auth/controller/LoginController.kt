package com.wafflestudio.toyproject.waffle5gramserver.auth.controller

import com.wafflestudio.toyproject.waffle5gramserver.auth.dto.AccessTokenResponseDto
import com.wafflestudio.toyproject.waffle5gramserver.auth.dto.LoginRequestDto
import com.wafflestudio.toyproject.waffle5gramserver.auth.jwt.JwtUtils
import com.wafflestudio.toyproject.waffle5gramserver.auth.service.UserAuthService
import org.springframework.http.HttpHeaders
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
    private val jwtUtils: JwtUtils
) {

    @PostMapping("/api/v1/auth/login")
    fun login(@RequestBody loginRequest: LoginRequestDto): ResponseEntity<AccessTokenResponseDto> {
        userAuthService.authenticateUsernamePassword(loginRequest.username, loginRequest.password)
        val accessToken = jwtUtils.generateAccessToken(loginRequest.username)
        val refreshToken = jwtUtils.generateRefreshToken(loginRequest.username)
        return successfulResponse(accessToken, refreshToken)
    }

    @ExceptionHandler(AuthenticationException::class)
    fun handleLoginAuthenticationException(ex: AuthenticationException): ResponseEntity<Any> {
        return ResponseEntity(mapOf("error" to "User not found"), HttpStatus.NOT_FOUND)
    }

    private fun successfulResponse(
        accessToken: String,
        refreshToken: String
    ): ResponseEntity<AccessTokenResponseDto> {
        val headers = HttpHeaders()
        headers.add(
            "Set-Cookie",
            """
            refresh_token=$refreshToken;
            HttpOnly;
            Path='/api/v1/auth/refresh_token';
            SameSite=Strict
            """.trimIndent().replace("\n", "")
        )
        val body = AccessTokenResponseDto(accessToken)
        return ResponseEntity<AccessTokenResponseDto>(body, headers, HttpStatus.CREATED)
    }
}
