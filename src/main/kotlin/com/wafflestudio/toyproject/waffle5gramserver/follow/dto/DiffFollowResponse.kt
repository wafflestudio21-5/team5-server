package com.wafflestudio.toyproject.waffle5gramserver.follow.dto

import com.wafflestudio.toyproject.waffle5gramserver.user.dto.MiniProfile

data class DiffFollowResponse(
    val count: Long,
    val miniProfiles: MutableList<MiniProfile>,
)
