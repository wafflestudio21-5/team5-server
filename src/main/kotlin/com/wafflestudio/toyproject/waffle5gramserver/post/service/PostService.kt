package com.wafflestudio.toyproject.waffle5gramserver.post.service

import kotlin.Long as Long

interface PostService {
    fun get(postId: Long, userId: Long): PostDetail
    fun create(content: String, fileUrls: List<String>, disableComment: Boolean, hideLike: Boolean, userId: Long): PostBrief
    fun patch(postId: Long, content: String, userId: Long): PostBrief
    fun delete(postId: Long, userId: Long)
}
