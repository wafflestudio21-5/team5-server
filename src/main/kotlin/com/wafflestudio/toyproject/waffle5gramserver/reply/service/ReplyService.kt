package com.wafflestudio.toyproject.waffle5gramserver.reply.service

interface ReplyService {
    fun getReply(page: Long, limit: Long): List<Reply>
    fun createReply(content: String): Reply
    fun updateReply(replyId: Long, content: String): Reply
    fun deleteReply(replyId: Long)
}