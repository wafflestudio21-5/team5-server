package com.wafflestudio.toyproject.waffle5gramserver.post.service

import java.time.LocalDateTime

data class PostBrief(
    val id: Long,
    val content: String,
    val createdAt: LocalDateTime,
)
