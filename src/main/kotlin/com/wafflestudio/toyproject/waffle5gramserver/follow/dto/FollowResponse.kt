package com.wafflestudio.toyproject.waffle5gramserver.follow.dto

data class FollowResponse(
    val followsId: Long,
    val followerUserId: Long,
    val followeeUserId: Long,
)
