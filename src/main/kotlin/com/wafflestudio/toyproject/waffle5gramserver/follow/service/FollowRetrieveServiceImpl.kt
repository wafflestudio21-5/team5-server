package com.wafflestudio.toyproject.waffle5gramserver.follow.service

import com.wafflestudio.toyproject.waffle5gramserver.follow.dto.CommonFollowResponse
import com.wafflestudio.toyproject.waffle5gramserver.follow.dto.DiffFollowResponse
import com.wafflestudio.toyproject.waffle5gramserver.follow.repository.FollowRepository
import com.wafflestudio.toyproject.waffle5gramserver.follow.repository.FollowRequestRepository
import com.wafflestudio.toyproject.waffle5gramserver.user.repository.UserRepository
import com.wafflestudio.toyproject.waffle5gramserver.user.service.InstagramUser
import org.springframework.stereotype.Service

@Service
class FollowRetrieveServiceImpl(
    private val followRequestRepository: FollowRequestRepository,
    private val followRepository: FollowRepository,
    private val userRepository: UserRepository,
) : FollowRetrieveService {
    override fun getFollower(
        authuser: InstagramUser,
        followerUserId: Long
    ) {
        // 팔로워 여부 조회 -> 아직 마구현, 리턴타입은 없음, 에러로 get 결과 제어
    }

    override fun getUserFollow(
        authuser: InstagramUser,
        userId: Long
    ) {
        // 유저 팔로우 여부 조회 -> 아직 마구현, 리턴타입은 없음, 에러로 get 결과 제어
    }

    override fun getCommonUserBetweenUsersFollowerAndAuthUsersFollowing(
        authuser: InstagramUser,
        userId: Long
    ): CommonFollowResponse {
        // 유저의 팔로워 및 현재 유저의 팔로잉 공통 조회 -> 아직 미구현
        return CommonFollowResponse(1, mutableListOf())
    }

    override fun getDifferenceBetweenUsersFollowerAndAuthUsersFollowing(
        authuser: InstagramUser,
        userId: Long
    ): DiffFollowResponse {
        // 유저의 팔로워 중 현재 유저의 팔로잉이 아닌 목록 조회 -> 아직 미구현
        return DiffFollowResponse(1, mutableListOf())
    }

    override fun getCommonFollowingBetweenUserAndAuthUser(
        authuser: InstagramUser,
        userId: Long
    ): CommonFollowResponse {
        // 유저의 팔로잉 및 현재 유저의 팔로잉 공통 목록 조회 -> 아직 미구현
        return CommonFollowResponse(1, mutableListOf())
    }

    override fun getDifferenceBetweenUsersFollowingAndAuthUsersFollowing(
        authuser: InstagramUser,
        userId: Long
    ): DiffFollowResponse {
        // 유저의 팔로잉 중 현재 유저의 팔로잉이 아닌 목록 조회 -> 아직 미구현
        return DiffFollowResponse(1, mutableListOf())
    }
}
