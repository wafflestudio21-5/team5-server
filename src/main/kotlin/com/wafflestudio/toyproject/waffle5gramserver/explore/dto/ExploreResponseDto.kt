package com.wafflestudio.toyproject.waffle5gramserver.explore.dto

import com.wafflestudio.toyproject.waffle5gramserver.feed.controller.PageInfo

data class ExploreResponseDto(
    val previews: List<ExplorePreview>,
    val pageInfo: PageInfo
)
