package com.wafflestudio.toyproject.waffle5gramserver.post.service

import com.wafflestudio.toyproject.waffle5gramserver.post.repository.MediaType
import java.time.LocalDateTime

data class PostMedia(
    val id: Long,
    val postId: Long,
    val order: Int,
    val url: String,
    val mediaType: MediaType,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
)
