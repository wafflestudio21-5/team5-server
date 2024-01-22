package com.wafflestudio.toyproject.waffle5gramserver.feed.service

import com.wafflestudio.toyproject.waffle5gramserver.post.service.PostDetail

interface UserFeedService {
    fun getUserFeedPreview(
        userId: Long,
        cursor: Long?,
        limit: Int,
    ): List<PostPreview>

    fun getPostDetails(postId: Long): PostDetail

    fun loadNewerPosts(
        userId: Long,
        cursor: Long?,
        limit: Int,
    ): List<PostDetail>

    fun loadOlderPosts(
        userId: Long,
        cursor: Long?,
        limit: Int,
    ): List<PostDetail>
}
