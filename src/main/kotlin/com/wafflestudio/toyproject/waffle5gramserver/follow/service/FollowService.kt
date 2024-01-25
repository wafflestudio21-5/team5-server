package com.wafflestudio.toyproject.waffle5gramserver.follow.service

import com.wafflestudio.toyproject.waffle5gramserver.follow.dto.FollowResponse
import com.wafflestudio.toyproject.waffle5gramserver.user.service.InstagramUser

interface FollowService {
    fun postFollowNonPrivateUser(authuser: InstagramUser, username: String): FollowResponse

    fun deleteFollowUser(authuser: InstagramUser, username: String)

    fun removeFollower(authuser: InstagramUser, followerUsername: String)
}
