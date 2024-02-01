package com.wafflestudio.toyproject.waffle5gramserver.feed.service

import com.wafflestudio.toyproject.waffle5gramserver.post.service.PostDetail
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Slice

interface SavedFeedService {
    fun getSavedFeedPreview(
        userId: Long,
        pageable: Pageable,
    ): Slice<PostPreview>

    fun getSavedFeed(
        userId: Long,
    ): List<PostDetail>
}
