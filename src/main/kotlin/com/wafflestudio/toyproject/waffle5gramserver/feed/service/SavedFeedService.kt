package com.wafflestudio.toyproject.waffle5gramserver.feed.service

import com.wafflestudio.toyproject.waffle5gramserver.post.service.PostDetail

interface SavedFeedService {
    fun getSavedFeedPreview(
        userId: Long,
        cursor: Long?,
        limit: Int,
    ): List<PostPreview>

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
