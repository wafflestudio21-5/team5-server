package com.wafflestudio.toyproject.waffle5gramserver.follow.service

import com.wafflestudio.toyproject.waffle5gramserver.follow.dto.FollowRequestResponse
import com.wafflestudio.toyproject.waffle5gramserver.follow.dto.FollowResponse
import com.wafflestudio.toyproject.waffle5gramserver.user.dto.MiniProfile
import com.wafflestudio.toyproject.waffle5gramserver.user.service.InstagramUser

interface FollowRequestService {
    fun getFollowRequestToPrivateUser(authuser: InstagramUser, userId: Long): FollowRequestResponse

    fun postFollowToPrivateUser(authuser: InstagramUser, userId: Long): FollowRequestResponse

    fun removeFollowRequestToPrivateUser(authuser: InstagramUser, userId: Long)

    fun getFollowRequestUserList(authuser: InstagramUser): MutableList<MiniProfile>

    fun getUserFollowRequest(authuser: InstagramUser, followerUserId: Long): FollowRequestResponse

    fun postFollowRequest(authuser: InstagramUser, followerUserId: Long): FollowResponse

    fun removeFollowRequest(authuser: InstagramUser, followerUserId: Long)
}
