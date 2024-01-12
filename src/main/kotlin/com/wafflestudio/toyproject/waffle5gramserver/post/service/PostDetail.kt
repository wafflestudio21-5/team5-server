package com.wafflestudio.toyproject.waffle5gramserver.post.service

import org.springframework.boot.autoconfigure.security.SecurityProperties.User
import java.time.LocalDateTime
import javax.print.attribute.standard.Media

data class PostDetail(
    val id: Long,
    val author: User,
    val content: String,
    val media: List<Media>,
    val liked: Boolean,
    val likeCount: Int,
    val commentCount: Int,
    val comments: List<Comment>,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
)
