package com.wafflestudio.toyproject.waffle5gramserver.follow.service

import com.wafflestudio.toyproject.waffle5gramserver.follow.dto.FollowRequestResponse
import com.wafflestudio.toyproject.waffle5gramserver.follow.dto.FollowResponse
import com.wafflestudio.toyproject.waffle5gramserver.follow.exception.PrivateException
import com.wafflestudio.toyproject.waffle5gramserver.follow.repository.FollowEntity
import com.wafflestudio.toyproject.waffle5gramserver.follow.repository.FollowRepository
import com.wafflestudio.toyproject.waffle5gramserver.follow.repository.FollowRequestEntity
import com.wafflestudio.toyproject.waffle5gramserver.follow.repository.FollowRequestRepository
import com.wafflestudio.toyproject.waffle5gramserver.global.error_handling.ErrorCode
import com.wafflestudio.toyproject.waffle5gramserver.global.error_handling.global_exception.EntityAlreadyExistException
import com.wafflestudio.toyproject.waffle5gramserver.global.error_handling.global_exception.EntityNotFoundException
import com.wafflestudio.toyproject.waffle5gramserver.user.dto.MiniProfile
import com.wafflestudio.toyproject.waffle5gramserver.user.repository.UserRepository
import com.wafflestudio.toyproject.waffle5gramserver.user.service.InstagramUser
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class FollowRequestServiceImpl(
    private val followRequestRepository: FollowRequestRepository,
    private val followRepository: FollowRepository,
    private val userRepository: UserRepository,
) : FollowRequestService {
    @Transactional
    override fun getFollowRequestToPrivateUser(
        authuser: InstagramUser,
        username: String,
    ): FollowRequestResponse {
        val user = userRepository.findByUsername(username)
            .orElseThrow { EntityNotFoundException(ErrorCode.USER_NOT_FOUND) }
        if (!user.isPrivate) throw PrivateException(ErrorCode.FOLLOWER_NOT_PRIVATE)
        if (followRepository.findByFollowerUserIdAndFolloweeUserId(authuser.id, user.id) != null) {
            throw EntityAlreadyExistException(ErrorCode.ALREADY_FOLLOW)
        }
        val followRequest = followRequestRepository.findByFollowerUserIdAndFolloweeUserId(authuser.id, user.id)
        if (followRequest == null) throw EntityNotFoundException(ErrorCode.REQUEST_NOT_FOUND)
        else {
            return FollowRequestResponse(
                followRequest.id,
                followRequest.follower.id,
                followRequest.followee.id
            )
        }
    }

    @Transactional
    override fun postFollowToPrivateUser(
        authuser: InstagramUser,
        username: String
    ): FollowRequestResponse {
        val followee = userRepository.findByUsername(username)
            .orElseThrow { EntityNotFoundException(ErrorCode.USER_NOT_FOUND) }
        if (!followee.isPrivate) throw PrivateException(ErrorCode.FOLLOWER_NOT_PRIVATE)
        if (followRepository.findByFollowerUserIdAndFolloweeUserId(authuser.id, followee.id) != null) {
            throw EntityAlreadyExistException(ErrorCode.ALREADY_FOLLOW)
        }
        if (followRequestRepository.findByFollowerUserIdAndFolloweeUserId(authuser.id, followee.id) != null) {
            throw EntityAlreadyExistException(ErrorCode.REQUEST_ALREADY_SENT)
        }
        val follower = userRepository.findById(authuser.id)
            .orElseThrow { EntityNotFoundException(ErrorCode.USER_NOT_FOUND) }
        followRequestRepository.save(
            FollowRequestEntity(
                follower = follower,
                followee = followee,
            )
        )
        val followRequest = followRequestRepository.findByFollowerUserIdAndFolloweeUserId(authuser.id, followee.id)
        return FollowRequestResponse(followRequest!!.id, followRequest.follower.id, followRequest.followee.id)
    }

    @Transactional
    override fun removeFollowRequestToPrivateUser(
        authuser: InstagramUser,
        username: String
    ) {
        val followee = userRepository.findByUsername(username)
            .orElseThrow { EntityNotFoundException(ErrorCode.USER_NOT_FOUND) }
        val followRequest = followRequestRepository.findByFollowerUserIdAndFolloweeUserId(authuser.id, followee.id)
        if (followRequest == null) throw EntityNotFoundException(ErrorCode.REQUEST_NOT_FOUND)
        else followRequestRepository.delete(followRequest)
    }

    @Transactional
    override fun getFollowRequestUserList(
        authuser: InstagramUser
    ): MutableList<MiniProfile> {
        val followRequestList = followRequestRepository.findAllByFolloweeUserId(authuser.id)
        val miniProfileList: MutableList<MiniProfile> = followRequestList.map { it ->
            MiniProfile(
                it.follower.id,
                it.follower.username,
                it.follower.name,
                it.follower.profileImageUrl
            )
        }.toMutableList()
        return miniProfileList
    }

    @Transactional
    override fun getUserFollowRequest(
        authuser: InstagramUser,
        followerUsername: String
    ): FollowRequestResponse {
        val follower = userRepository.findByUsername(followerUsername)
            .orElseThrow { EntityNotFoundException(ErrorCode.USER_NOT_FOUND) }
        val followRequestResponse = followRequestRepository.findByFollowerUserIdAndFolloweeUserId(follower.id, authuser.id)
        if (followRequestResponse == null) throw EntityNotFoundException(ErrorCode.REQUEST_NOT_FOUND)
        else {
            return FollowRequestResponse(
                followRequestResponse.id,
                followRequestResponse.follower.id,
                followRequestResponse.followee.id,
            )
        }
    }

    @Transactional
    override fun postFollowRequest(
        authuser: InstagramUser,
        followerUsername: String,
    ): FollowResponse {
        // error handling
        val follower = userRepository.findByUsername(followerUsername)
            .orElseThrow { EntityNotFoundException(ErrorCode.USER_NOT_FOUND) }
        val follow = followRepository.findByFollowerUserIdAndFolloweeUserId(follower.id, authuser.id)
        if (follow != null) throw EntityAlreadyExistException(ErrorCode.ALREADY_FOLLOW)
        val followRequest =
            followRequestRepository.findByFollowerUserIdAndFolloweeUserId(follower.id, authuser.id)
                ?: throw EntityNotFoundException(ErrorCode.REQUEST_NOT_FOUND)
        // main logic
        followRequestRepository.delete(followRequest)
        val followee = userRepository.findById(authuser.id)
            .orElseThrow { EntityNotFoundException(ErrorCode.USER_NOT_FOUND) }
        followRepository.save(FollowEntity(follower, followee))
        val followResponse = followRepository.findByFollowerUserIdAndFolloweeUserId(follower.id, followee.id)
        return FollowResponse(followResponse!!.id, followResponse.follower.id, followResponse.followee.id)
    }

    @Transactional
    override fun removeFollowRequest(
        authuser: InstagramUser,
        followerUsername: String
    ) {
        val follower = userRepository.findByUsername(followerUsername)
            .orElseThrow { EntityNotFoundException(ErrorCode.USER_NOT_FOUND) }
        val followRequest =
            followRequestRepository.findByFollowerUserIdAndFolloweeUserId(follower.id, authuser.id)
                ?: throw EntityNotFoundException(ErrorCode.REQUEST_NOT_FOUND)
        followRequestRepository.delete(followRequest)
    }
}
