package com.wafflestudio.toyproject.waffle5gramserver.auth.controller

import com.wafflestudio.toyproject.waffle5gramserver.auth.dto.AccessTokenResponseDto
import com.wafflestudio.toyproject.waffle5gramserver.auth.dto.FacebookSignUpRequestDto
import com.wafflestudio.toyproject.waffle5gramserver.auth.service.CustomOAuth2UserService
import com.wafflestudio.toyproject.waffle5gramserver.auth.service.TokenRefreshService
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class FacebookSignUpController(
    private val tokenRefreshService: TokenRefreshService,
    private val customOAuth2UserService: CustomOAuth2UserService
) {
    @PostMapping("/api/v1/auth/facebook_signup")
    fun signUp(
        @RequestBody @Valid facebookSignUpRequestDto: FacebookSignUpRequestDto,
        request: HttpServletRequest,
        response: HttpServletResponse
    ): ResponseEntity<AccessTokenResponseDto> {
        val refreshToken = tokenRefreshService.extractRefreshToken(request)
        val temporaryUsername = tokenRefreshService.validateRefreshToken(refreshToken)
        val newUsername = customOAuth2UserService.updateFacebookOAuth2User(
            temporaryUsername = temporaryUsername,
            newUsername = facebookSignUpRequestDto.username,
            birthday = facebookSignUpRequestDto.birthday
        )
        val newAccessToken = tokenRefreshService.generateNewAccessToken(newUsername)
        val newRefreshToken = tokenRefreshService.generateNewRefreshToken(newUsername)
        tokenRefreshService.addRefreshTokenCookie(response, newRefreshToken, "/api/v1/auth/refresh_token")
        return ResponseEntity<AccessTokenResponseDto>(
            AccessTokenResponseDto(accessToken = newAccessToken),
            HttpStatus.CREATED
        )
    }
}
