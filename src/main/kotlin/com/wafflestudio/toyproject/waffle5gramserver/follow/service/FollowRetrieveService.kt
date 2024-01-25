package com.wafflestudio.toyproject.waffle5gramserver.follow.service

import com.wafflestudio.toyproject.waffle5gramserver.follow.dto.CommonFollowResponse
import com.wafflestudio.toyproject.waffle5gramserver.follow.dto.DiffFollowResponse
import com.wafflestudio.toyproject.waffle5gramserver.user.service.InstagramUser

interface FollowRetrieveService {
    fun getFollower(authuser: InstagramUser, followerUsername: String)

    fun getUserFollow(authuser: InstagramUser, username: String)

    fun getCommonUserBetweenUsersFollowerAndAuthUsersFollowing(authuser: InstagramUser, username: String): CommonFollowResponse

    fun getDifferenceBetweenUsersFollowerAndAuthUsersFollowing(authuser: InstagramUser, username: String): DiffFollowResponse

    fun getCommonFollowingBetweenUserAndAuthUser(authuser: InstagramUser, username: String): CommonFollowResponse

    fun getDifferenceBetweenUsersFollowingAndAuthUsersFollowing(authuser: InstagramUser, username: String): DiffFollowResponse
}
