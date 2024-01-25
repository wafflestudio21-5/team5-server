package com.wafflestudio.toyproject.waffle5gramserver.follow.service

import com.wafflestudio.toyproject.waffle5gramserver.follow.dto.FollowRequestResponse
import com.wafflestudio.toyproject.waffle5gramserver.follow.dto.FollowResponse
import com.wafflestudio.toyproject.waffle5gramserver.user.dto.MiniProfile
import com.wafflestudio.toyproject.waffle5gramserver.user.service.InstagramUser

interface FollowRequestService {
    fun getFollowRequestToPrivateUser(authuser: InstagramUser, username: String): FollowRequestResponse

    fun postFollowToPrivateUser(authuser: InstagramUser, username: String): FollowRequestResponse

    fun removeFollowRequestToPrivateUser(authuser: InstagramUser, username: String)

    fun getFollowRequestUserList(authuser: InstagramUser): MutableList<MiniProfile>

    fun getUserFollowRequest(authuser: InstagramUser, followerUsername: String): FollowRequestResponse

    fun postFollowRequest(authuser: InstagramUser, followerUsername: String): FollowResponse

    fun removeFollowRequest(authuser: InstagramUser, followerUsername: String)
}
