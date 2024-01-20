package com.wafflestudio.toyproject.waffle5gramserver.reply.service

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface ReplyService {
    fun getReplies(
        commentId: Long,
        pageable: Pageable,
    ): Page<Reply>

    fun create(
        commentId: Long,
        content: String,
        userId: Long,
    ): Reply

    fun patch(
        replyId: Long,
        content: String,
        userId: Long,
    ): Reply

    fun delete(
        replyId: Long,
        userId: Long,
    )
}
