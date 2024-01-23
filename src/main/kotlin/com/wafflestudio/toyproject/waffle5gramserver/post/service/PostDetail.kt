package com.wafflestudio.toyproject.waffle5gramserver.post.service

import java.time.LocalDateTime

data class PostDetail(
    val id: Long,
    val author: PostAuthor,
    val content: String,
    val media: List<PostMedia>,
    val liked: Boolean,
    val likeCount: Int,
    val saved: Boolean,
    val commentCount: Int,
    val hideLike: Boolean,
    val createdAt: LocalDateTime,
)
