package com.wafflestudio.toyproject.waffle5gramserver.post.service

import java.time.LocalDateTime

data class PostDetail(
    val id: Long,
    val author: Long,
    val content: String,
    val media: List<PostMedia>,
    val liked: Boolean,
    val likeCount: Int,
    val commentCount: Int,
    val disableComment: Boolean,
    val hideLike: Boolean,
    val createdAt: LocalDateTime,
)
