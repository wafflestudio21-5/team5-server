package com.wafflestudio.toyproject.waffle5gramserver.search.dto

import com.wafflestudio.toyproject.waffle5gramserver.feed.controller.PageInfo
import com.wafflestudio.toyproject.waffle5gramserver.user.dto.MiniProfile

data class MiniProfilePageResponse(
    val miniProfiles: MutableList<MiniProfile>?,
    val pageInfo: PageInfo,
)
