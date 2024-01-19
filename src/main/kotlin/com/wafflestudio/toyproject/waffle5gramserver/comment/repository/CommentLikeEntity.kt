package com.wafflestudio.toyproject.waffle5gramserver.comment.repository

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity(name = "comment_likes")
class CommentLikeEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,
    val userId: Long = 0L,
    val commentId: Long = 0L,
    val createdAt: Long = 0L,
)
