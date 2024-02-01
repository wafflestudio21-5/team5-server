package com.wafflestudio.toyproject.waffle5gramserver.follow.dto

data class DiffFollowResponse(
    val count: Long,
    val miniProfiles: MutableList<IsRequestMiniProfile>,
)
