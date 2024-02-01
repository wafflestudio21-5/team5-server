package com.wafflestudio.toyproject.waffle5gramserver.feed.service

import com.wafflestudio.toyproject.waffle5gramserver.post.service.PostDetail

interface SavedFeedService {
    fun getSavedFeedPreview(userId: Long): List<PostPreview>

    fun getSavedFeed(userId: Long): List<PostDetail>
}
