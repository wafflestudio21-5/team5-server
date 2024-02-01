package com.wafflestudio.toyproject.waffle5gramserver.explore.dto

import com.wafflestudio.toyproject.waffle5gramserver.feed.controller.PageInfo
import com.wafflestudio.toyproject.waffle5gramserver.post.service.PostDetail

data class ExploreFeedResponseDto(
    val posts: List<PostDetail>,
    val pageInfo: PageInfo
)
