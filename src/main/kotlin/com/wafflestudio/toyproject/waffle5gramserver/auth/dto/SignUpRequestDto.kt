package com.wafflestudio.toyproject.waffle5gramserver.auth.dto

import com.wafflestudio.toyproject.waffle5gramserver.auth.validation.ValidSignUpRequest
import com.wafflestudio.toyproject.waffle5gramserver.user.repository.ContactType
import jakarta.validation.constraints.NotBlank
import java.util.Date

@ValidSignUpRequest
data class SignUpRequestDto(
    @NotBlank
    val username: String,
    @NotBlank
    val name: String,
    @NotBlank
    val password: String,
    @NotBlank
    val contact: String,
    @NotBlank
    val contactType: ContactType,
    @NotBlank
    val birthday: Date
)
