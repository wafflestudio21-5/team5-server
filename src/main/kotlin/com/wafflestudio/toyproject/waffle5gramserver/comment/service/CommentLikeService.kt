package com.wafflestudio.toyproject.waffle5gramserver.comment.service

interface CommentLikeService {
    fun exists(
        commentId: Long,
        userId: Long,
    ): Boolean

    fun create(
        commentId: Long,
        userId: Long,
    )

    fun delete(
        commentId: Long,
        userId: Long,
    )
}
