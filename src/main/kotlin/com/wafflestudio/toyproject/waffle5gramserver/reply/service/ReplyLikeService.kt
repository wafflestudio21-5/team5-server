package com.wafflestudio.toyproject.waffle5gramserver.reply.service

interface ReplyLikeService {
    fun exists(
        replyId: Long,
        userId: Long,
    ): Boolean

    fun create(
        replyId: Long,
        userId: Long,
    )

    fun delete(
        replyId: Long,
        userId: Long,
    )
}
