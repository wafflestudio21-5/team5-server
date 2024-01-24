package com.wafflestudio.toyproject.waffle5gramserver.follow.controller

import com.wafflestudio.toyproject.waffle5gramserver.follow.exception.UserHimselfException
import com.wafflestudio.toyproject.waffle5gramserver.follow.service.FollowService
import com.wafflestudio.toyproject.waffle5gramserver.global.error_handling.ErrorCode
import com.wafflestudio.toyproject.waffle5gramserver.global.result_handling.ResultCode
import com.wafflestudio.toyproject.waffle5gramserver.global.result_handling.ResultResponse
import com.wafflestudio.toyproject.waffle5gramserver.user.service.InstagramUser
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/friendship")
class FollowController(
    private val followService: FollowService,
) {

    // 공개 유저 팔로우
    @PostMapping("/{userId}/follow")
    fun followNonPrivateUser(
        @AuthenticationPrincipal user: InstagramUser,
        @RequestParam userId: Long,
    ): ResponseEntity<ResultResponse> {
        if (user.id == userId) throw UserHimselfException(ErrorCode.USER_HIMSELF)
        val followResponse = followService.postFollowNonPrivateUser(user, userId)
        return ResponseEntity.ok(ResultResponse.of(ResultCode.FOLLOW_SUCCESS, followResponse))
    }

    // 유저 언팔로우
    @DeleteMapping("/{userId}/follow")
    fun unfollowUser(
        @AuthenticationPrincipal user: InstagramUser,
        @RequestParam userId: Long,
    ): ResponseEntity<ResultResponse> {
        if (user.id == userId) throw UserHimselfException(ErrorCode.USER_HIMSELF)
        followService.deleteFollowUser(user, userId)
        return ResponseEntity.ok(ResultResponse.of(ResultCode.UNFOLLOW_SUCCESS))
    }

    // 팔로워 삭제
    @DeleteMapping("/{followerUserId}/follower")
    fun deleteFollower(
        @AuthenticationPrincipal user: InstagramUser,
        @RequestParam followerUserId: Long,
    ): ResponseEntity<ResultResponse> {
        if (user.id == followerUserId) throw UserHimselfException(ErrorCode.FOLLOWER_HIMSELF)
        followService.removeFollower(user, followerUserId)
        return ResponseEntity.ok(ResultResponse.of(ResultCode.DELETE_FOLLOWER_SUCCESS))
    }
}
