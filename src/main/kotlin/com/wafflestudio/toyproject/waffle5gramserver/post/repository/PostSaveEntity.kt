package com.wafflestudio.toyproject.waffle5gramserver.post.repository

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity(name = "post_saves")
class PostSaveEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0L,
    val userId: Long = 0L,
    val postId: Long = 0L,
    val createdAt: Long = 0L,
)
