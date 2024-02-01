package com.wafflestudio.toyproject.waffle5gramserver.feed.service

import com.wafflestudio.toyproject.waffle5gramserver.post.service.PostDetail
import com.wafflestudio.toyproject.waffle5gramserver.user.service.InstagramUser
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Slice

interface UserFeedService {
    fun getUserFeedPreview(
        authuser: InstagramUser,
        username: String,
        pageable: Pageable,
    ): Slice<PostPreview>

    fun getUserFeed(
        authuser: InstagramUser,
        username: String,
        pageable: Pageable,
    ): Slice<PostDetail>
}
