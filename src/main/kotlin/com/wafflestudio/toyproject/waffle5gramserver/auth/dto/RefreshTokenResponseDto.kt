package com.wafflestudio.toyproject.waffle5gramserver.auth.dto

data class RefreshTokenResponseDto(
    val username: String,
    val accessToken: String,
)
