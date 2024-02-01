package com.wafflestudio.toyproject.waffle5gramserver.feed.controller

import com.wafflestudio.toyproject.waffle5gramserver.feed.service.PostPreview

data class FeedPreviewResponse(
    val posts: List<PostPreview>,
    val pageInfo: PageInfo,
)
