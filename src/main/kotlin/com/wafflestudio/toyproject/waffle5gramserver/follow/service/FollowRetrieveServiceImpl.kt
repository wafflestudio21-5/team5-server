package com.wafflestudio.toyproject.waffle5gramserver.follow.service

import com.wafflestudio.toyproject.waffle5gramserver.follow.dto.CommonFollowResponse
import com.wafflestudio.toyproject.waffle5gramserver.follow.dto.DiffFollowResponse
import com.wafflestudio.toyproject.waffle5gramserver.follow.dto.IsRequestMiniProfile
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
        followerUsername: String
    ) {
        val follower = userRepository.findByUsername(followerUsername)
            .orElseThrow { EntityNotFoundException(ErrorCode.USER_NOT_FOUND) }
        val followee = userRepository.findById(authuser.id)
            .orElseThrow { EntityNotFoundException(ErrorCode.USER_NOT_FOUND) }
        val follow = followRepository.findByFollowerUserIdAndFolloweeUserId(follower.id, followee.id)
            ?: throw EntityNotFoundException(ErrorCode.NOT_FOLLOWER)
        follow.equals(follow) // 업로드가 안되어서 더미로 넣음
    }

    @Transactional
    override fun getUserFollow(
        authuser: InstagramUser,
        username: String
    ) {
        val followee = userRepository.findByUsername(username)
            .orElseThrow { EntityNotFoundException(ErrorCode.USER_NOT_FOUND) }
        val follower = userRepository.findById(authuser.id)
            .orElseThrow { EntityNotFoundException(ErrorCode.USER_NOT_FOUND) }
        val follow = followRepository.findByFollowerUserIdAndFolloweeUserId(follower.id, followee.id)
            ?: throw EntityNotFoundException(ErrorCode.USER_NOT_FOLLOW)
        follow.equals(follow) // 업로드가 안되어서 더미로 넣음
    }

    @Transactional
    override fun getCommonUserBetweenUsersFollowerAndAuthUsersFollowing(
        authuser: InstagramUser,
        username: String
    ): CommonFollowResponse {
        val user = userRepository.findByUsername(username)
            .orElseThrow { EntityNotFoundException(ErrorCode.USER_NOT_FOUND) }
        if (user.id != authuser.id) {
            if (followRepository.findByFollowerUserIdAndFolloweeUserId(authuser.id, user.id) == null) {
                if (user.isPrivate) {
                    throw PrivateException(ErrorCode.USER_PRIVATE_NOT_FOLLOWING)
                }
            }
        }
        val usernow = userRepository.findById(authuser.id)
            .orElseThrow { EntityNotFoundException(ErrorCode.USER_NOT_FOUND) }
        val usersfollowerEntity = followRepository.findAllByFolloweeUserId(user.id)
        val usernowsfollowingEntity = followRepository.findAllByFollowerUserId(usernow.id)
        if (usersfollowerEntity.isEmpty() || usernowsfollowingEntity.isEmpty()) {
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
        username: String,
    ): DiffFollowResponse {
        val user = userRepository.findByUsername(username)
            .orElseThrow { EntityNotFoundException(ErrorCode.USER_NOT_FOUND) }
        if (user.id != authuser.id) {
            if (followRepository.findByFollowerUserIdAndFolloweeUserId(authuser.id, user.id) == null) {
                if (user.isPrivate) {
                    throw PrivateException(ErrorCode.USER_PRIVATE_NOT_FOLLOWING)
                }
            }
        }
        val usernow = userRepository.findById(authuser.id)
            .orElseThrow { EntityNotFoundException(ErrorCode.USER_NOT_FOUND) }
        val usersfollower = followRepository.findAllByFolloweeUserId(user.id).map { it.follower }
        val usernowsfollowing = followRepository.findAllByFollowerUserId(usernow.id).map { it.followee }

        val followRequestUsers = followRequestRepository.findAllByFollowerUserId(authuser.id).map { it.followee }

        if (usersfollower.isEmpty()) {
            return DiffFollowResponse(0, mutableListOf())
        } else if (usernowsfollowing.isEmpty()) {
            val diffNotRequest = usersfollower.subtract(followRequestUsers)
                .map {
                    IsRequestMiniProfile(
                        it.id,
                        it.username,
                        it.name,
                        it.profileImageUrl,
                        false,
                    )
                }.toMutableList()
            val diffRequest = usersfollower.intersect(followRequestUsers)
                .map {
                    IsRequestMiniProfile(
                        it.id,
                        it.username,
                        it.name,
                        it.profileImageUrl,
                        true,
                    )
                }.toMutableList()
            val result = diffNotRequest.union(diffRequest).toMutableList()
            return DiffFollowResponse(result.count().toLong(), result)
        } else {
            val diffEntity = usersfollower.subtract(usernowsfollowing)

            val diffNotRequest = diffEntity.subtract(followRequestUsers)
                .map {
                    IsRequestMiniProfile(
                        it.id,
                        it.username,
                        it.name,
                        it.profileImageUrl,
                        false,
                    )
                }.toMutableList()
            val diffRequest = diffEntity.intersect(followRequestUsers)
                .map {
                    IsRequestMiniProfile(
                        it.id,
                        it.username,
                        it.name,
                        it.profileImageUrl,
                        true,
                    )
                }.toMutableList()
            val result = diffNotRequest.union(diffRequest).toMutableList()

            return DiffFollowResponse(result.count().toLong(), result)
        }
    }

    @Transactional
    override fun getCommonFollowingBetweenUserAndAuthUser(
        authuser: InstagramUser,
        username: String
    ): CommonFollowResponse {
        val user = userRepository.findByUsername(username)
            .orElseThrow { EntityNotFoundException(ErrorCode.USER_NOT_FOUND) }
        if (user.id != authuser.id) {
            if (followRepository.findByFollowerUserIdAndFolloweeUserId(authuser.id, user.id) == null) {
                if (user.isPrivate) {
                    throw PrivateException(ErrorCode.USER_PRIVATE_NOT_FOLLOWING)
                }
            }
        }
        val usernow = userRepository.findById(authuser.id)
            .orElseThrow { EntityNotFoundException(ErrorCode.USER_NOT_FOUND) }
        val usersfollowingEntity = followRepository.findAllByFollowerUserId(user.id)
        val usernowsfollowingEntity = followRepository.findAllByFollowerUserId(usernow.id)
        if (usersfollowingEntity.isEmpty() || usernowsfollowingEntity.isEmpty()) {
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
        username: String
    ): DiffFollowResponse {
        val user = userRepository.findByUsername(username)
            .orElseThrow { EntityNotFoundException(ErrorCode.USER_NOT_FOUND) }
        if (user.id != authuser.id) {
            if (followRepository.findByFollowerUserIdAndFolloweeUserId(authuser.id, user.id) == null) {
                if (user.isPrivate) {
                    throw PrivateException(ErrorCode.USER_PRIVATE_NOT_FOLLOWING)
                }
            }
        }
        val usernow = userRepository.findById(authuser.id)
            .orElseThrow { EntityNotFoundException(ErrorCode.USER_NOT_FOUND) }
        val usersfollowing = followRepository.findAllByFollowerUserId(user.id).map { it.followee }
        val usernowsfollowing = followRepository.findAllByFollowerUserId(usernow.id).map { it.followee }

        val followRequestUsers = followRequestRepository.findAllByFollowerUserId(authuser.id).map { it.followee }

        if (usersfollowing.isEmpty()) {
            return DiffFollowResponse(0, mutableListOf())
        } else if (usernowsfollowing.isEmpty()) {

            val diffNotRequest = usersfollowing.subtract(followRequestUsers)
                .map {
                    IsRequestMiniProfile(
                        it.id,
                        it.username,
                        it.name,
                        it.profileImageUrl,
                        false,
                    )
                }.toMutableList()
            val diffRequest = usersfollowing.intersect(followRequestUsers)
                .map {
                    IsRequestMiniProfile(
                        it.id,
                        it.username,
                        it.name,
                        it.profileImageUrl,
                        true,
                    )
                }.toMutableList()
            val result = diffNotRequest.union(diffRequest).toMutableList()

            return DiffFollowResponse(result.count().toLong(), result)
        } else {
            val diffEntity = usersfollowing.subtract(usernowsfollowing)

            val diffNotRequest = diffEntity.subtract(followRequestUsers)
                .map {
                    IsRequestMiniProfile(
                        it.id,
                        it.username,
                        it.name,
                        it.profileImageUrl,
                        false,
                    )
                }.toMutableList()
            val diffRequest = diffEntity.intersect(followRequestUsers)
                .map {
                    IsRequestMiniProfile(
                        it.id,
                        it.username,
                        it.name,
                        it.profileImageUrl,
                        true,
                    )
                }.toMutableList()
            val result = diffNotRequest.union(diffRequest).toMutableList()

            return DiffFollowResponse(result.count().toLong(), result)
        }
    }
}
