package com.wafflestudio.toyproject.waffle5gramserver.comment.service

interface CommentService {
    fun getComments()
    fun createComment(postId: Long, content: String): CommentBrief
    fun updateComment(commentId: Long, content: String): CommentBrief
    fun deleteComment(commentId: Long)
}