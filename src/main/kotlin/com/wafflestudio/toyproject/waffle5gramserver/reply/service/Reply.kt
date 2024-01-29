package com.wafflestudio.toyproject.waffle5gramserver.reply.service

import java.time.LocalDateTime

data class Reply(
    val id: Long,
    val commentId: Long,
    val userId: Long,
    val username: String,
    val userProfileImageUrl: String?,
    val content: String,
    val likeCount: Long,
    val createdAt: LocalDateTime,
    val liked: Boolean,
)
