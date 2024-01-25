package com.wafflestudio.toyproject.waffle5gramserver.follow.service

import com.wafflestudio.toyproject.waffle5gramserver.follow.dto.FollowResponse
import com.wafflestudio.toyproject.waffle5gramserver.follow.exception.PrivateException
import com.wafflestudio.toyproject.waffle5gramserver.follow.repository.FollowEntity
import com.wafflestudio.toyproject.waffle5gramserver.follow.repository.FollowRepository
import com.wafflestudio.toyproject.waffle5gramserver.follow.repository.FollowRequestRepository
import com.wafflestudio.toyproject.waffle5gramserver.global.error_handling.ErrorCode
import com.wafflestudio.toyproject.waffle5gramserver.global.error_handling.global_exception.EntityAlreadyExistException
import com.wafflestudio.toyproject.waffle5gramserver.global.error_handling.global_exception.EntityNotFoundException
import com.wafflestudio.toyproject.waffle5gramserver.user.repository.UserRepository
import com.wafflestudio.toyproject.waffle5gramserver.user.service.InstagramUser
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class FollowServiceImpl(
    private val followRepository: FollowRepository,
    private val followRequestRepository: FollowRequestRepository,
    private val userRepository: UserRepository,
) : FollowService {

    @Transactional
    override fun postFollowNonPrivateUser(
        authuser: InstagramUser,
        username: String
    ): FollowResponse {
        val followee = userRepository.findByUsername(username)
            .orElseThrow { EntityNotFoundException(ErrorCode.USER_NOT_FOUND) }
        if (followee.isPrivate) throw PrivateException(ErrorCode.USER_NOT_NON_PRIVATE)
        val follower = userRepository.findById(authuser.id)
            .orElseThrow { EntityNotFoundException(ErrorCode.USER_NOT_FOUND) }
        val followResponse = followRepository.findByFollowerUserIdAndFolloweeUserId(follower.id, followee.id)
        if (followResponse != null) throw EntityAlreadyExistException(ErrorCode.ALREADY_FOLLOW)
        else followRepository.save(FollowEntity(follower, followee))
        val follow = followRepository.findByFollowerUserIdAndFolloweeUserId(follower.id, followee.id)
        return FollowResponse(follow!!.id, follow.follower.id, follow.followee.id)
    }

    @Transactional
    override fun deleteFollowUser(
        authuser: InstagramUser,
        username: String
    ) {
        val followee = userRepository.findByUsername(username)
            .orElseThrow { EntityNotFoundException(ErrorCode.USER_NOT_FOUND) }
        val follower = userRepository.findById(authuser.id)
            .orElseThrow { EntityNotFoundException(ErrorCode.USER_NOT_FOUND) }
        val follow = followRepository.findByFollowerUserIdAndFolloweeUserId(follower.id, followee.id)
            ?: throw EntityNotFoundException(ErrorCode.ALREADY_NOT_FOLLOW)
        followRepository.delete(follow)
    }

    @Transactional
    override fun removeFollower(
        authuser: InstagramUser,
        followerUsername: String
    ) {
        val follower = userRepository.findByUsername(followerUsername)
            .orElseThrow { EntityNotFoundException(ErrorCode.USER_NOT_FOUND) }
        val followee = userRepository.findById(authuser.id)
            .orElseThrow { EntityNotFoundException(ErrorCode.USER_NOT_FOUND) }
        val follow = followRepository.findByFollowerUserIdAndFolloweeUserId(follower.id, followee.id)
            ?: throw EntityNotFoundException(ErrorCode.ALREADY_NOT_FOLLOWER)
        followRepository.delete(follow)
    }
}
