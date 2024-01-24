package com.wafflestudio.toyproject.waffle5gramserver.follow.service

import com.wafflestudio.toyproject.waffle5gramserver.follow.dto.FollowResponse
import com.wafflestudio.toyproject.waffle5gramserver.user.service.InstagramUser

interface FollowService {
    fun postFollowNonPrivateUser(authuser: InstagramUser, userId: Long): FollowResponse

    fun deleteFollowUser(authuser: InstagramUser, userId: Long)

    fun removeFollower(authuser: InstagramUser, followerUserId: Long)
}
