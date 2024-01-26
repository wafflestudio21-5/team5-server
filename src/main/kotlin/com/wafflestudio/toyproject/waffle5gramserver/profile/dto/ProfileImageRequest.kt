package com.wafflestudio.toyproject.waffle5gramserver.profile.dto

import jakarta.validation.constraints.NotBlank
import org.hibernate.validator.constraints.URL

// TODO
data class ProfileImageRequest(
    @URL
    @NotBlank
    val profileImageUrl: String,
    val message: String,
)
