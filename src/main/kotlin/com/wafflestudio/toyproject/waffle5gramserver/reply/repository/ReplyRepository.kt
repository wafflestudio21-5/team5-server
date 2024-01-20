package com.wafflestudio.toyproject.waffle5gramserver.reply.repository

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

interface ReplyRepository : JpaRepository<ReplyEntity, Long> {
    fun findByCommentId(
        commentId: Long,
        pageable: Pageable,
    ): Page<ReplyEntity>

    fun countByCommentId(commentId: Long): Long
}
