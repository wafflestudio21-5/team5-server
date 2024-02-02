package com.wafflestudio.toyproject.waffle5gramserver.search.dto

import jakarta.validation.constraints.NotBlank

data class UsernameRequestDto(
    @NotBlank
    val username: String,
)
