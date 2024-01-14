package com.wafflestudio.toyproject.waffle5gramserver.post.service

import com.wafflestudio.toyproject.waffle5gramserver.user.repository.UserEntity
import java.time.LocalDateTime

data class PostDetail(
    val id: Long,
    val author: UserEntity,
    val content: String,
    val media: List<PostMedia>,
    val liked: Boolean,
    val likeCount: Int,
    val commentCount: Int,
    val hideLike: Boolean,
    val createdAt: LocalDateTime,
)
