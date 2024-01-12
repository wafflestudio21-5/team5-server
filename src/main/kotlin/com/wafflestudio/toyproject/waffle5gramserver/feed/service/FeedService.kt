package com.wafflestudio.toyproject.waffle5gramserver.feed.service

import com.wafflestudio.toyproject.waffle5gramserver.post.service.PostDetail
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface FeedService {
    fun getHomeFeed(userId: Long, pageable: Pageable): Page<PostDetail>
    fun getFeedWithPostId(postId: Long): List<PostDetail>
}