package com.wafflestudio.toyproject.waffle5gramserver.follow.service

import com.wafflestudio.toyproject.waffle5gramserver.follow.dto.FollowResponse
import com.wafflestudio.toyproject.waffle5gramserver.follow.repository.FollowRepository
import com.wafflestudio.toyproject.waffle5gramserver.follow.repository.FollowRequestRepository
import com.wafflestudio.toyproject.waffle5gramserver.user.repository.UserRepository
import com.wafflestudio.toyproject.waffle5gramserver.user.service.InstagramUser
import org.springframework.stereotype.Service

@Service
class FollowServiceImpl(
    private val followRepository: FollowRepository,
    private val followRequestRepository: FollowRequestRepository,
    private val userRepository: UserRepository,
) : FollowService {

    override fun postFollowNonPrivateUser(
        authuser: InstagramUser,
        userId: Long
    ): FollowResponse {
        // 공개 유저 팔로우 -> 아직 미구현
        return FollowResponse(1, 1, 1)
    }

    override fun deleteFollowUser(
        authuser: InstagramUser,
        userId: Long
    ) {
        // 유저 언팔로우 -> 아직 미구현, 리턴 타입 없음
    }

    override fun removeFollower(
        authuser: InstagramUser,
        followerUserId: Long
    ) {
        // 팔로워 삭제 -> 아직 미구현, 리턴 타입 없음
    }
}
