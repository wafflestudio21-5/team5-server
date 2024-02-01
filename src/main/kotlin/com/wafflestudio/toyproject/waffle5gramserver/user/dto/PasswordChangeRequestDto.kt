package com.wafflestudio.toyproject.waffle5gramserver.user.dto

data class PasswordChangeRequestDto(
    val oldPassword: String,
    val newPassword: String,
)
