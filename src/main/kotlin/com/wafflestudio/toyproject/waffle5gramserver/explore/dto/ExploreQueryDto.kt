package com.wafflestudio.toyproject.waffle5gramserver.explore.dto

import com.wafflestudio.toyproject.waffle5gramserver.post.repository.PostCategory

data class ExploreQueryDto(
    val page: Int = 0,
    val size: Int = 6,
    val category: PostCategory? = null,
)
