package com.wafflestudio.toyproject.waffle5gramserver.follow.dto

import com.wafflestudio.toyproject.waffle5gramserver.user.dto.MiniProfile

data class CommonFollowResponse(
    val commonNumber: Long,
    val miniProfiles: MutableList<MiniProfile>,
)
