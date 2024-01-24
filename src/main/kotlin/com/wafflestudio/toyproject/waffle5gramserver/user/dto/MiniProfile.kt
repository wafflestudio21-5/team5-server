package com.wafflestudio.toyproject.waffle5gramserver.user.dto

data class MiniProfile(
    val userId: Long,
    val username: String,
    val name: String,
    val profileImageUrl: String?,
)
