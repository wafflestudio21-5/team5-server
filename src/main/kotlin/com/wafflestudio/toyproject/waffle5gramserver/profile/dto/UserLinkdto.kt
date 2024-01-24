package com.wafflestudio.toyproject.waffle5gramserver.profile.dto

import jakarta.validation.constraints.NotBlank
import org.hibernate.validator.constraints.URL

data class UserLinkdto(
    val linkId: Long,
    val linkTitle: String?,
    @NotBlank
    @URL
    val link: String,
)
