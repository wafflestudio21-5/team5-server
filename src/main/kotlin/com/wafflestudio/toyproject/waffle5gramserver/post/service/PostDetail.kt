package com.wafflestudio.toyproject.waffle5gramserver.post.service

import com.wafflestudio.toyproject.waffle5gramserver.comment.repository.CommentEntity
import java.time.LocalDateTime

data class PostDetail(
    val id: Long,
    val userId: Long,
    val content: String,
    val media: List<PostMedia>,
    val liked: Boolean,
    val likeCount: Int,
    val commentCount: Int,
    val comments: List<CommentEntity>,
    val disableComment: Boolean,
    val hideLike: Boolean,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
)
