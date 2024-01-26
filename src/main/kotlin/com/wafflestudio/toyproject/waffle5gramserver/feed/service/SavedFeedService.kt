package com.wafflestudio.toyproject.waffle5gramserver.feed.service

import com.wafflestudio.toyproject.waffle5gramserver.post.service.PostDetail

interface SavedFeedService {
    fun getSavedFeedPreview(
        username: String,
        cursor: Long?,
        limit: Int,
    ): List<PostPreview>

    fun loadNewerPosts(
        username: String,
        cursor: Long?,
        limit: Int,
    ): List<PostDetail>

    fun loadOlderPosts(
        username: String,
        cursor: Long?,
        limit: Int,
    ): List<PostDetail>
}