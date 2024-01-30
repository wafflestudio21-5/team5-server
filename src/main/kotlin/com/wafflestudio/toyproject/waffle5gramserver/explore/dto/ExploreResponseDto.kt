package com.wafflestudio.toyproject.waffle5gramserver.explore.dto

import com.wafflestudio.toyproject.waffle5gramserver.feed.controller.PageInfo
import com.wafflestudio.toyproject.waffle5gramserver.post.service.PostMediasBrief

data class ExploreResponseDto(
    val postMediasBriefList: List<PostMediasBrief>,
    val pageInfo: PageInfo
)
