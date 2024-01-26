package com.wafflestudio.toyproject.waffle5gramserver.profile.dto

import jakarta.validation.constraints.NotBlank

data class NameRequest(
    @NotBlank
    val name: String,
    val message: String,
)
