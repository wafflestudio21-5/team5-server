package com.wafflestudio.toyproject.waffle5gramserver.post.service

import com.wafflestudio.toyproject.waffle5gramserver.post.repository.PostEntity

interface PostEntityWithCommentCount {
    fun getPostEntity(): PostEntity
    fun getCommentCount(): Long
}
