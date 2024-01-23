package com.wafflestudio.toyproject.waffle5gramserver.follow.dto

import com.wafflestudio.toyproject.waffle5gramserver.user.dto.MiniProfile

data class DiffFollowResponse(
    val diffNumber: Long,
    val miniProfile: MutableList<MiniProfile>,
)
