package com.wafflestudio.toyproject.waffle5gramserver.post.repository

interface PostEntityWithCommentCount {
    fun getPostEntity(): PostEntity
    fun getCommentCount(): Long
}
