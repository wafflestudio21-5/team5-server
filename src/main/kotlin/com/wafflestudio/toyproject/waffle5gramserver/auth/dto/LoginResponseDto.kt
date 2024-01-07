package com.wafflestudio.toyproject.waffle5gramserver.auth.dto

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming

@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy::class)
data class LoginResponseDto(
    val accessToken: String
)
