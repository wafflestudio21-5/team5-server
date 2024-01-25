package com.wafflestudio.toyproject.waffle5gramserver.feed.service

import com.wafflestudio.toyproject.waffle5gramserver.post.service.PostDetail
import com.wafflestudio.toyproject.waffle5gramserver.user.service.InstagramUser

interface UserFeedService {
    fun getUserFeedPreview(
        authuser: InstagramUser,
        username: String,
        cursor: Long?,
        limit: Int,
    ): List<PostPreview>

    fun loadNewerPosts(
        authuser: InstagramUser,
        username: String,
        cursor: Long?,
        limit: Int,
    ): List<PostDetail>

    fun loadOlderPosts(
        authuser: InstagramUser,
        username: String,
        cursor: Long?,
        limit: Int,
    ): List<PostDetail>
}
