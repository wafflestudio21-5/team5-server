package com.wafflestudio.toyproject.waffle5gramserver.follow.controller

import com.wafflestudio.toyproject.waffle5gramserver.follow.dto.FollowResponse
import com.wafflestudio.toyproject.waffle5gramserver.follow.exception.UserHimselfException
import com.wafflestudio.toyproject.waffle5gramserver.follow.service.FollowService
import com.wafflestudio.toyproject.waffle5gramserver.global.error_handling.ErrorCode
import com.wafflestudio.toyproject.waffle5gramserver.global.result_handling.ResultCode
import com.wafflestudio.toyproject.waffle5gramserver.global.result_handling.ResultResponse
import com.wafflestudio.toyproject.waffle5gramserver.user.service.InstagramUser
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/friendship")
class FollowController(
    private val followService: FollowService,
) {

    // 공개 유저 팔로우
    @PostMapping("/{username}/follow")
    fun followNonPrivateUser(
        @AuthenticationPrincipal user: InstagramUser,
        @PathVariable("username") username: String,
    ): ResponseEntity</*ResultResponse*/FollowResponse> {
        if (user.username == username) throw UserHimselfException(ErrorCode.USER_HIMSELF)
        val followResponse = followService.postFollowNonPrivateUser(user, username)
        // return ResponseEntity.ok(ResultResponse.of(ResultCode.FOLLOW_SUCCESS, followResponse))
        return ResponseEntity.ok(followResponse)
    }

    // 유저 언팔로우
    @DeleteMapping("/{username}/follow")
    fun unfollowUser(
        @AuthenticationPrincipal user: InstagramUser,
        @PathVariable("username") username: String,
    ): ResponseEntity<ResultResponse> {
        if (user.username == username) throw UserHimselfException(ErrorCode.USER_HIMSELF)
        followService.deleteFollowUser(user, username)
        return ResponseEntity.ok(ResultResponse.of(ResultCode.UNFOLLOW_SUCCESS))
    }

    // 팔로워 삭제
    @DeleteMapping("/{followerUsername}/follower")
    fun deleteFollower(
        @AuthenticationPrincipal user: InstagramUser,
        @PathVariable("followerUsername") followerUsername: String,
    ): ResponseEntity<ResultResponse> {
        if (user.username == followerUsername) throw UserHimselfException(ErrorCode.FOLLOWER_HIMSELF)
        followService.removeFollower(user, followerUsername)
        return ResponseEntity.ok(ResultResponse.of(ResultCode.DELETE_FOLLOWER_SUCCESS))
    }
}
