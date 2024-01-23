package com.wafflestudio.toyproject.waffle5gramserver.follow.service

import com.wafflestudio.toyproject.waffle5gramserver.follow.dto.CommonFollowResponse
import com.wafflestudio.toyproject.waffle5gramserver.follow.dto.DiffFollowResponse
import com.wafflestudio.toyproject.waffle5gramserver.user.service.InstagramUser

interface FollowRetrieveService {
    fun getFollower(authuser: InstagramUser, followerUserId: Long)

    fun getUserFollow(authuser: InstagramUser, userId: Long)

    fun getCommonUserBetweenUsersFollowerAndAuthUsersFollowing(authuser: InstagramUser, userId: Long) : CommonFollowResponse

    fun getDifferenceBetweenUsersFollowerAndAuthUsersFollowing(authuser: InstagramUser, userId: Long) : DiffFollowResponse

    fun getCommonFollowingBetweenUserAndAuthUser(authuser: InstagramUser, userId: Long) : CommonFollowResponse

    fun getDifferenceBetweenUsersFollowingAndAuthUsersFollowing(authuser: InstagramUser, userId: Long) : DiffFollowResponse
}