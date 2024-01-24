package com.wafflestudio.toyproject.waffle5gramserver.follow.service

import com.wafflestudio.toyproject.waffle5gramserver.follow.dto.CommonFollowResponse
import com.wafflestudio.toyproject.waffle5gramserver.follow.dto.DiffFollowResponse
import com.wafflestudio.toyproject.waffle5gramserver.follow.exception.PrivateException
import com.wafflestudio.toyproject.waffle5gramserver.follow.repository.FollowRepository
import com.wafflestudio.toyproject.waffle5gramserver.follow.repository.FollowRequestRepository
import com.wafflestudio.toyproject.waffle5gramserver.global.error_handling.ErrorCode
import com.wafflestudio.toyproject.waffle5gramserver.global.error_handling.global_exception.EntityNotFoundException
import com.wafflestudio.toyproject.waffle5gramserver.user.dto.MiniProfile
import com.wafflestudio.toyproject.waffle5gramserver.user.repository.UserRepository
import com.wafflestudio.toyproject.waffle5gramserver.user.service.InstagramUser
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class FollowRetrieveServiceImpl(
    private val followRequestRepository: FollowRequestRepository,
    private val followRepository: FollowRepository,
    private val userRepository: UserRepository,
) : FollowRetrieveService {
    @Transactional
    override fun getFollower(
        authuser: InstagramUser,
        followerUserId: Long
    ) {
        val follower = userRepository.findById(followerUserId)
            .orElseThrow { EntityNotFoundException(ErrorCode.USER_NOT_FOUND) }
        val followee = userRepository.findById(authuser.id)
            .orElseThrow { EntityNotFoundException(ErrorCode.USER_NOT_FOUND) }
        val follow = followRepository.findByFollowerUserIdAndFolloweeUserId(follower.id, followee.id)
            ?: throw EntityNotFoundException(ErrorCode.NOT_FOLLOWER)
    }

    @Transactional
    override fun getUserFollow(
        authuser: InstagramUser,
        userId: Long
    ) {
        val followee = userRepository.findById(userId)
            .orElseThrow { EntityNotFoundException(ErrorCode.USER_NOT_FOUND) }
        val follower = userRepository.findById(authuser.id)
            .orElseThrow { EntityNotFoundException(ErrorCode.USER_NOT_FOUND) }
        val follow = followRepository.findByFollowerUserIdAndFolloweeUserId(follower.id, followee.id)
            ?: throw EntityNotFoundException(ErrorCode.USER_NOT_FOLLOW)
    }

    @Transactional
    override fun getCommonUserBetweenUsersFollowerAndAuthUsersFollowing(
        authuser: InstagramUser,
        userId: Long
    ): CommonFollowResponse {
        val user = userRepository.findById(userId)
            .orElseThrow { EntityNotFoundException(ErrorCode.USER_NOT_FOUND) }
        if (userId != authuser.id) {
            if (followRepository.findByFollowerUserIdAndFolloweeUserId(authuser.id, userId) == null) {
                if (user.isPrivate) {
                    throw PrivateException(ErrorCode.USER_PRIVATE_NOT_FOLLOWING)
                }
            }
        }
        val usernow = userRepository.findById(authuser.id)
            .orElseThrow { EntityNotFoundException(ErrorCode.USER_NOT_FOUND) }
        val usersfollowerEntity = followRepository.findAllByFolloweeUserId(user.id)
        val usernowsfollowingEntity = followRepository.findAllByFollowerUserId(usernow.id)
        if (usersfollowerEntity == null || usernowsfollowingEntity == null) {
            return CommonFollowResponse(0, mutableListOf())
        } else {
            val usersfollower = usersfollowerEntity.map { it.follower }
            val usernowsfollowing = usernowsfollowingEntity.map { it.followee }
            val commonEntity = usersfollower.intersect(usernowsfollowing)
            val commonMiniProfiles: MutableList<MiniProfile> = commonEntity.map {
                MiniProfile(
                    it.id,
                    it.username,
                    it.name,
                    it.profileImageUrl
                )
            }.toMutableList()
            return CommonFollowResponse(commonMiniProfiles.count().toLong(), commonMiniProfiles)
        }
    }

    @Transactional
    override fun getDifferenceBetweenUsersFollowerAndAuthUsersFollowing(
        authuser: InstagramUser,
        userId: Long
    ): DiffFollowResponse {
        val user = userRepository.findById(userId)
            .orElseThrow { EntityNotFoundException(ErrorCode.USER_NOT_FOUND) }
        if (userId != authuser.id) {
            if (followRepository.findByFollowerUserIdAndFolloweeUserId(authuser.id, userId) == null) {
                if (user.isPrivate) {
                    throw PrivateException(ErrorCode.USER_PRIVATE_NOT_FOLLOWING)
                }
            }
        }
        val usernow = userRepository.findById(authuser.id)
            .orElseThrow { EntityNotFoundException(ErrorCode.USER_NOT_FOUND) }
        val usersfollowerEntity = followRepository.findAllByFolloweeUserId(user.id)
        val usernowsfollowingEntity = followRepository.findAllByFollowerUserId(usernow.id)
        if (usersfollowerEntity == null) {
            return DiffFollowResponse(0, mutableListOf())
        } else if (usernowsfollowingEntity == null) {
            val diffMiniProfiles = usersfollowerEntity.map { it.follower }.map {
                MiniProfile(
                    it.id,
                    it.username,
                    it.name,
                    it.profileImageUrl
                )
            }.toMutableList()
            return DiffFollowResponse(diffMiniProfiles.count().toLong(), diffMiniProfiles)
        } else {
            val usersfollower = usersfollowerEntity.map { it.follower }
            val usernowsfollowing = usernowsfollowingEntity.map { it.followee }
            val diffEntity = usersfollower.subtract(usernowsfollowing)
            val diffMiniProfiles: MutableList<MiniProfile> = diffEntity.map {
                MiniProfile(
                    it.id,
                    it.username,
                    it.name,
                    it.profileImageUrl
                )
            }.toMutableList()
            return DiffFollowResponse(diffMiniProfiles.count().toLong(), diffMiniProfiles)
        }
    }

    @Transactional
    override fun getCommonFollowingBetweenUserAndAuthUser(
        authuser: InstagramUser,
        userId: Long
    ): CommonFollowResponse {
        val user = userRepository.findById(userId)
            .orElseThrow { EntityNotFoundException(ErrorCode.USER_NOT_FOUND) }
        if (userId != authuser.id) {
            if (followRepository.findByFollowerUserIdAndFolloweeUserId(authuser.id, userId) == null) {
                if (user.isPrivate) {
                    throw PrivateException(ErrorCode.USER_PRIVATE_NOT_FOLLOWING)
                }
            }
        }
        val usernow = userRepository.findById(authuser.id)
            .orElseThrow { EntityNotFoundException(ErrorCode.USER_NOT_FOUND) }
        val usersfollowingEntity = followRepository.findAllByFollowerUserId(user.id)
        val usernowsfollowingEntity = followRepository.findAllByFollowerUserId(usernow.id)
        if (usersfollowingEntity == null || usernowsfollowingEntity == null) {
            return CommonFollowResponse(0, mutableListOf())
        } else {
            val usersfollowing = usersfollowingEntity.map { it.followee }
            val usernowsfollowing = usernowsfollowingEntity.map { it.followee }
            val commonEntity = usersfollowing.intersect(usernowsfollowing)
            val commonMiniProfiles: MutableList<MiniProfile> = commonEntity.map {
                MiniProfile(
                    it.id,
                    it.username,
                    it.name,
                    it.profileImageUrl
                )
            }.toMutableList()
            return CommonFollowResponse(commonMiniProfiles.count().toLong(), commonMiniProfiles)
        }
    }

    @Transactional
    override fun getDifferenceBetweenUsersFollowingAndAuthUsersFollowing(
        authuser: InstagramUser,
        userId: Long
    ): DiffFollowResponse {
        val user = userRepository.findById(userId)
            .orElseThrow { EntityNotFoundException(ErrorCode.USER_NOT_FOUND) }
        if (userId != authuser.id) {
            if (followRepository.findByFollowerUserIdAndFolloweeUserId(authuser.id, userId) == null) {
                if (user.isPrivate) {
                    throw PrivateException(ErrorCode.USER_PRIVATE_NOT_FOLLOWING)
                }
            }
        }
        val usernow = userRepository.findById(authuser.id)
            .orElseThrow { EntityNotFoundException(ErrorCode.USER_NOT_FOUND) }
        val usersfollowingEntity = followRepository.findAllByFollowerUserId(user.id)
        val usernowsfollowingEntity = followRepository.findAllByFollowerUserId(usernow.id)
        if (usersfollowingEntity == null) {
            return DiffFollowResponse(0, mutableListOf())
        } else if (usernowsfollowingEntity == null) {
            val diffMiniProfiles = usersfollowingEntity.map { it.followee }.map {
                MiniProfile(
                    it.id,
                    it.username,
                    it.name,
                    it.profileImageUrl
                )
            }.toMutableList()
            return DiffFollowResponse(diffMiniProfiles.count().toLong(), diffMiniProfiles)
        } else {
            val usersfollowing = usersfollowingEntity.map { it.followee }
            val usernowsfollowing = usernowsfollowingEntity.map { it.followee }
            val diffEntity = usersfollowing.subtract(usernowsfollowing)
            val diffMiniProfiles: MutableList<MiniProfile> = diffEntity.map {
                MiniProfile(
                    it.id,
                    it.username,
                    it.name,
                    it.profileImageUrl
                )
            }.toMutableList()
            return DiffFollowResponse(diffMiniProfiles.count().toLong(), diffMiniProfiles)
        }
    }
}
