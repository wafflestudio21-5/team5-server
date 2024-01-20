package com.wafflestudio.toyproject.waffle5gramserver.comment.repository

import org.springframework.data.jpa.repository.JpaRepository

interface CommentLikeRepository : JpaRepository<CommentLikeEntity, Long> {
    fun countByCommentId(commentId: Long): Long

    fun findByCommentIdAndUserId(
        commentId: Long,
        userId: Long,
    ): CommentLikeEntity?
}
