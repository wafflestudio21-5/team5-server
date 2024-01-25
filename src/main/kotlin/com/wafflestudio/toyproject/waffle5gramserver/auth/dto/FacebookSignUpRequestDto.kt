package com.wafflestudio.toyproject.waffle5gramserver.auth.dto

import jakarta.validation.constraints.NotBlank
import java.util.Date

class FacebookSignUpRequestDto(
    @NotBlank
    val username: String,
    @NotBlank
    val birthday: Date
)
