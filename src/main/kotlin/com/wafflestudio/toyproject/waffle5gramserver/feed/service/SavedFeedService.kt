package com.wafflestudio.toyproject.waffle5gramserver.feed.service

import com.wafflestudio.toyproject.waffle5gramserver.post.service.PostDetail
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Slice

interface SavedFeedService {
    fun getSavedFeedPreview(
        username: String,
        cursor: Long?,
        limit: Int,
    ): List<PostPreview>

    fun getSavedFeed(
        userId: Long,
        pageable: Pageable,
    ): Slice<PostDetail>
}
