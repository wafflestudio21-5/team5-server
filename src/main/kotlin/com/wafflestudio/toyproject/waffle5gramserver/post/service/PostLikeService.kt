package com.wafflestudio.toyproject.waffle5gramserver.post.service

interface PostLikeService {
    fun exists(postId: Long, userId: Long): Boolean
    fun create(postId: Long, userId: Long)
    fun delete(postId: Long, userId: Long)
}
