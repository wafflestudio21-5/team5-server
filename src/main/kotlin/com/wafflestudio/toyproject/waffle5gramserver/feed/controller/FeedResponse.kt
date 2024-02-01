package com.wafflestudio.toyproject.waffle5gramserver.feed.controller

import com.wafflestudio.toyproject.waffle5gramserver.post.service.PostDetail

data class FeedResponse(
    val posts: List<PostDetail>,
    val pageInfo: PageInfo,
)
