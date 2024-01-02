package com.wafflestudio.toyproject.waffle5gramserver.reply.service

data class Reply (
    val replyId: Long,
    val parentCommentId: Long,
    val userId: Long,
    val author: String,
    val content: String,
    val createdAt: String,
)