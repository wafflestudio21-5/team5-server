package com.wafflestudio.toyproject.waffle5gramserver.post.service

import com.wafflestudio.toyproject.waffle5gramserver.post.repository.PostCategory
import java.time.LocalDateTime

data class PostMediasBrief (
    val id: Long,
    val createdAt: LocalDateTime,
    val medias: List<PostMedia>,
    val category: PostCategory? = null,
    val likeCount: Int? = null,
    val commentCount: Long? = null,
)
