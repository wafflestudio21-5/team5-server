package com.wafflestudio.toyproject.waffle5gramserver.follow.service

import com.wafflestudio.toyproject.waffle5gramserver.follow.dto.FollowRequestResponse
import com.wafflestudio.toyproject.waffle5gramserver.follow.dto.FollowResponse
import com.wafflestudio.toyproject.waffle5gramserver.follow.repository.FollowRepository
import com.wafflestudio.toyproject.waffle5gramserver.follow.repository.FollowRequestRepository
import com.wafflestudio.toyproject.waffle5gramserver.user.dto.MiniProfile
import com.wafflestudio.toyproject.waffle5gramserver.user.repository.UserRepository
import com.wafflestudio.toyproject.waffle5gramserver.user.service.InstagramUser
import org.springframework.stereotype.Service

@Service
class FollowRequestServiceImpl(
    private val followRequestRepository: FollowRequestRepository,
    private val followRepository: FollowRepository,
    private val userRepository: UserRepository,
) : FollowRequestService {
    override fun getFollowRequestToPrivateUser(
        authuser: InstagramUser,
        userId: Long,
    ): FollowRequestResponse {
        // 비공개 유저 팔로우 요청 조회 -> 아직 미구현
        return FollowRequestResponse(1, 1, 1)
    }

    override fun postFollowToPrivateUser(
        authuser: InstagramUser,
        userId: Long
    ): FollowRequestResponse {
        // 비공개 유저 팔로우 요청 -> 아직 미구현
        return FollowRequestResponse(1, 1, 1)
    }

    override fun removeFollowRequestToPrivateUser(
        authuser: InstagramUser,
        userId: Long
    ) {
        // 비공개 유저 팔로우 요청 취소 -> 아직 미구현, 리턴타입은 없음
    }

    override fun getFollowRequestUserList(
        authuser: InstagramUser
    ): MutableList<MiniProfile> {
        // 팔로우 요청 유저 목록 조회 -> 아직 미구현
        return mutableListOf()
    }

    override fun getUserFollowRequest(
        authuser: InstagramUser,
        followerUserId: Long
    ): FollowRequestResponse {
        // 유저 팔로우 요청 조회 -> 아직 미구현
        return FollowRequestResponse(1, 1, 1)
    }

    override fun postFollowRequest(
        authuser: InstagramUser,
        followerUserId: Long
    ): FollowResponse {
        // 팔로우 요청 수락 -> 아직 미구현
        return FollowResponse(1, 1, 1)
    }

    override fun removeFollowRequest(
        authuser: InstagramUser,
        followerUserId: Long
    ) {
        // 팔로우 요청 거절 -> 아직 미구현, 리턴타입은 없음
    }
}
