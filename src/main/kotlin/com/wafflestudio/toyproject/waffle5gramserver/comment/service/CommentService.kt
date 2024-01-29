package com.wafflestudio.toyproject.waffle5gramserver.comment.service

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface CommentService {
    fun getComments(
        postId: Long,
        pageable: Pageable,
        userId: Long,
    ): Page<Comment>

    fun create(
        postId: Long,
        content: String,
        userId: Long,
    ): Comment

    fun patch(
        commentId: Long,
        content: String,
        userId: Long,
    ): Comment

    fun delete(
        commentId: Long,
        userId: Long,
    )
}
