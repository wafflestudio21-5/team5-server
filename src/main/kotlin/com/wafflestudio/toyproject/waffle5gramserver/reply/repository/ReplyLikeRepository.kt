package com.wafflestudio.toyproject.waffle5gramserver.reply.repository

import org.springframework.data.jpa.repository.JpaRepository

interface ReplyLikeRepository : JpaRepository<ReplyLikeEntity, Long> {
    fun countByReplyId(replyId: Long): Long

    fun findByReplyIdAndUserId(
        replyId: Long,
        userId: Long,
    ): ReplyLikeEntity?
}
