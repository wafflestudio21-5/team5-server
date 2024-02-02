package com.wafflestudio.toyproject.waffle5gramserver.search.dto

import jakarta.validation.constraints.NotBlank

data class TextRequestDto(
    @NotBlank
    val text: String,
)
