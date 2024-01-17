package com.wafflestudio.toyproject.waffle5gramserver.comment.service

import java.time.LocalDateTime

data class Comment(
    val id: Long,
    val postId: Long,
    val userId: Long,
    val username: String,
    val userProfileImageUrl: String,
    val text: String,
    val createdAt: LocalDateTime,
    val likeCount: Long,
//    val replyCount: Int,
)
