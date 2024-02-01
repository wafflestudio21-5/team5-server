package com.wafflestudio.toyproject.waffle5gramserver.feed.service

import com.wafflestudio.toyproject.waffle5gramserver.post.service.PostDetail
import com.wafflestudio.toyproject.waffle5gramserver.user.service.InstagramUser

interface UserFeedService {
    fun getUserFeedPreview(
        authuser: InstagramUser,
        username: String,
    ): List<PostPreview>

    fun getUserFeed(
        authuser: InstagramUser,
        username: String,
    ): List<PostDetail>
}
