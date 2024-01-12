package com.wafflestudio.toyproject.waffle5gramserver.post.service

import org.springframework.web.multipart.MultipartFile
import kotlin.Long as Long

interface PostService {
    fun get(postId: Long, userId: Long): PostDetail
    fun create(content: String, files: List<MultipartFile>, disableComment: Boolean, hideLike: Boolean, userId: Long): PostBrief
    fun patch(postId: Long, content: String, userId: Long): PostBrief
    fun delete(postId: Long, userId: Long)
}