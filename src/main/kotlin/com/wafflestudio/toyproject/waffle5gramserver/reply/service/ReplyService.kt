package com.wafflestudio.toyproject.waffle5gramserver.reply.service

interface ReplyService {
    fun getReplies(commentId: Long, page: Long, limit: Long): List<Reply>
    fun createReply(commentId: Long, content: String): ReplyBrief
    fun updateReply(replyId: Long, content: String): ReplyBrief
    fun deleteReply(replyId: Long)
}