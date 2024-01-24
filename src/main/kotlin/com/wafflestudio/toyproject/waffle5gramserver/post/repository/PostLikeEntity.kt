package com.wafflestudio.toyproject.waffle5gramserver.post.repository

import com.wafflestudio.toyproject.waffle5gramserver.BaseAuditingEntity
import jakarta.persistence.Entity

@Entity(name = "post_likes")
class PostLikeEntity(
    val userId: Long = 0L,
    val postId: Long = 0L,
) : BaseAuditingEntity()
