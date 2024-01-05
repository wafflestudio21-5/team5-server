package com.wafflestudio.toyproject.waffle5gramserver.comment.service

interface CommentService {
    fun getComments()
    fun createComment()
    fun updateComment()
    fun deleteComment()
}