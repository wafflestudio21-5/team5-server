package com.wafflestudio.toyproject.waffle5gramserver.follow.dto

data class FollowRequestResponse(
    val followRequestsId: Long,
    val followerUserId: Long,
    val followeeUserId: Long,
)
