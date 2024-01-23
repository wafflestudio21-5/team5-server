package com.wafflestudio.toyproject.waffle5gramserver.feed.service

import com.wafflestudio.toyproject.waffle5gramserver.post.service.PostDetail
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Slice

interface FeedService {
    fun getHomeFeed(userId: Long, pageable: Pageable): Slice<PostDetail>
    fun getFeedWithPostId(postId: Long): List<PostDetail>
}
