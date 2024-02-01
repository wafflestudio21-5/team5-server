package com.wafflestudio.toyproject.waffle5gramserver.follow.dto

data class IsRequestMiniProfile(
    val userId: Long,
    val username: String,
    val name: String,
    val profileImageUrl: String?,
    val isRequest: Boolean,
)
