package com.wafflestudio.toyproject.waffle5gramserver.reply.service

data class Reply (
    val reply_id: Long,
    val parent_comment_id: Long,
    val user_id: Long,
    val author: String,
    val content: String,
    val created_at: String,
)