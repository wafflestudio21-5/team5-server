package com.wafflestudio.toyproject.waffle5gramserver.follow.controller

import com.wafflestudio.toyproject.waffle5gramserver.follow.exception.UserHimselfException
import com.wafflestudio.toyproject.waffle5gramserver.follow.service.FollowRetrieveService
import com.wafflestudio.toyproject.waffle5gramserver.global.error_handling.ErrorCode
import com.wafflestudio.toyproject.waffle5gramserver.global.result_handling.ResultCode
import com.wafflestudio.toyproject.waffle5gramserver.global.result_handling.ResultResponse
import com.wafflestudio.toyproject.waffle5gramserver.user.service.InstagramUser
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/friendship")
class FollowRetrieveController(
    private val followRetrieveService: FollowRetrieveService,
) {

    // 팔로워 여부 조회
    @GetMapping("/{followerUserId}/follower")
    fun retrieveFollower(
        @AuthenticationPrincipal user: InstagramUser,
        @RequestParam followerUserId: Long,
    ): ResponseEntity<ResultResponse> {
        if (user.id == followerUserId) throw UserHimselfException(ErrorCode.FOLLOWER_HIMSELF)
        followRetrieveService.getFollower(user, followerUserId)
        return ResponseEntity.ok(ResultResponse.of(ResultCode.RETRIEVE_FOLLOWER_SUCCESS))
    }

    // 유저 팔로우 여부 조회
    @GetMapping("/{userId}/follow")
    fun retrieveUserFollow(
        @AuthenticationPrincipal user: InstagramUser,
        @RequestParam userId: Long,
    ): ResponseEntity<ResultResponse> {
        if (user.id == userId) throw UserHimselfException(ErrorCode.FOLLOWER_HIMSELF)
        followRetrieveService.getUserFollow(user, userId)
        return ResponseEntity.ok(ResultResponse.of(ResultCode.GET_USER_FOLLOW_SUCCESS))
    }

    // 유저의 팔로워 및 현재 유저의 팔로잉 공통 목록 조회
    @GetMapping("/{userId}/follower/common")
    fun retrieveCommonUserBetweenUsersFollowerAndAuthUsersFollowing(
        @AuthenticationPrincipal user: InstagramUser,
        @RequestParam userId: Long,
    ): ResponseEntity<ResultResponse> {
        val commonFollowResponse = followRetrieveService.getCommonUserBetweenUsersFollowerAndAuthUsersFollowing(user, userId)
        return ResponseEntity.ok(ResultResponse.of(ResultCode.GET_COMMON_FOLLOWER_FOLLOWING, commonFollowResponse))
    }

    // 유저의 팔로워 중 현재 유저의 팔로잉이 아닌 목록 조회
    @GetMapping("/{userId}/follower/diff")
    fun retrieveDifferenceBetweenUsersFollowerAndAuthUsersFollowing(
        @AuthenticationPrincipal user: InstagramUser,
        @RequestParam userId: Long,
    ): ResponseEntity<ResultResponse> {
        val diffFollowResponse = followRetrieveService.getDifferenceBetweenUsersFollowerAndAuthUsersFollowing(user, userId)
        return ResponseEntity.ok(ResultResponse.of(ResultCode.GET_DIFF_FOLLOWER_FOLLOWING, diffFollowResponse))
    }

    // 유저의 팔로잉 및 현재 유저의 팔로잉 공통 목록 조회
    @GetMapping("/{userId}/following/common")
    fun retrieveCommonFollowingBetweenUserAndAuthUser(
        @AuthenticationPrincipal user: InstagramUser,
        @RequestParam userId: Long,
    ): ResponseEntity<ResultResponse> {
        val commonFollowResponse = followRetrieveService.getCommonFollowingBetweenUserAndAuthUser(user, userId)
        return ResponseEntity.ok(ResultResponse.of(ResultCode.GET_COMMON_FOLLOWING_FOLLOWING, commonFollowResponse))
    }

    // 유저의 팔로잉 중 현재 유저의 팔로잉이 아닌 목록 조회
    @GetMapping("/{userId}/following/diff")
    fun retrieveDifferenceBetweenUsersFollowingAndAuthUsersFollowing(
        @AuthenticationPrincipal user: InstagramUser,
        @RequestParam userId: Long,
    ): ResponseEntity<ResultResponse> {
        val diffFollowResponse = followRetrieveService.getDifferenceBetweenUsersFollowingAndAuthUsersFollowing(user, userId)
        return ResponseEntity.ok(ResultResponse.of(ResultCode.GET_DIFF_FOLLOWING_FOLLOWING, diffFollowResponse))
    }
}
