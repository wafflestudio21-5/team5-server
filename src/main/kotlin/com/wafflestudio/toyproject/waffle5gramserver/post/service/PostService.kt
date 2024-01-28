package com.wafflestudio.toyproject.waffle5gramserver.post.service

import com.wafflestudio.toyproject.waffle5gramserver.post.repository.PostCategory
import kotlin.Long as Long

interface PostService {
    fun get(
        postId: Long,
        userId: Long,
    ): PostDetail

    fun create(
        content: String,
        fileUrls: List<String>,
        disableComment: Boolean,
        hideLike: Boolean,
        userId: Long,
        category: PostCategory
    ): PostBrief

    fun patch(
        postId: Long,
        content: String,
        userId: Long,
    ): PostBrief

    fun delete(
        postId: Long,
        userId: Long,
    )
}
