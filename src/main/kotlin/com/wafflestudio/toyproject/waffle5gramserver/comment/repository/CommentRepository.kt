package com.wafflestudio.toyproject.waffle5gramserver.comment.repository

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

interface CommentRepository : JpaRepository<CommentEntity, Long> {
    fun findByPostId(
        postId: Long,
        pageable: Pageable,
    ): Page<CommentEntity>

    fun countByPostId(postId: Long): Long
}
