package com.wafflestudio.toyproject.waffle5gramserver.profile.dto

import jakarta.validation.constraints.NotBlank

data class UsernameRequest(
    @NotBlank
    val username: String,
    val message: String,
)
