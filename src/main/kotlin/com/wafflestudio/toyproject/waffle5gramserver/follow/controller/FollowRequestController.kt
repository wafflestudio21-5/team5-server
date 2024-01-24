package com.wafflestudio.toyproject.waffle5gramserver.follow.controller

import com.wafflestudio.toyproject.waffle5gramserver.follow.exception.PrivateException
import com.wafflestudio.toyproject.waffle5gramserver.follow.exception.UserHimselfException
import com.wafflestudio.toyproject.waffle5gramserver.follow.service.FollowRequestService
import com.wafflestudio.toyproject.waffle5gramserver.global.error_handling.ErrorCode
import com.wafflestudio.toyproject.waffle5gramserver.global.result_handling.ResultCode
import com.wafflestudio.toyproject.waffle5gramserver.global.result_handling.ResultResponse
import com.wafflestudio.toyproject.waffle5gramserver.user.service.InstagramUser
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/friendship")
class FollowRequestController(
    private val followRequestService: FollowRequestService,
) {
    // 비공개 유저 팔로우 요청 조회
    @GetMapping("/{userId}/follow/request")
    fun retrieveFollowRequestToPrivateUser(
        @AuthenticationPrincipal user: InstagramUser,
        @PathVariable("userId") userId: Long,
    ): ResponseEntity<ResultResponse> {
        if (user.id == userId) throw UserHimselfException(ErrorCode.USER_HIMSELF)
        val followRequestResponse = followRequestService.getFollowRequestToPrivateUser(user, userId)
        return ResponseEntity.ok(ResultResponse.of(ResultCode.ALREADY_REQUEST_FOLLOW, followRequestResponse))
    }

    // 비공개 유저 팔로우 요청
    @PostMapping("/{userId}/follow/request")
    fun requestFollowToPrivateUser(
        @AuthenticationPrincipal user: InstagramUser,
        @PathVariable("userId") userId: Long,
    ): ResponseEntity<ResultResponse> {
        if (user.id == userId) throw UserHimselfException(ErrorCode.USER_HIMSELF)
        val followRequestResponse = followRequestService.postFollowToPrivateUser(user, userId)
        return ResponseEntity.ok(ResultResponse.of(ResultCode.REQUEST_FOLLOW_SUCCESS, followRequestResponse))
    }

    // 비공개 유저 팔로우 요청 취소
    @DeleteMapping("/{userId}/follow/request")
    fun deleteFollowRequestToPrivateUser(
        @AuthenticationPrincipal user: InstagramUser,
        @PathVariable("userId") userId: Long,
    ): ResponseEntity<ResultResponse> {
        if (user.id == userId) throw UserHimselfException(ErrorCode.USER_HIMSELF)
        followRequestService.removeFollowRequestToPrivateUser(user, userId)
        return ResponseEntity.ok(ResultResponse.of(ResultCode.DELETE_FOLLOW_SUCCESS))
    }

    // 팔로우 요청 유저 목록 조회
    @GetMapping("/requestlist")
    fun retrieveFollowRequestUserList(
        @AuthenticationPrincipal user: InstagramUser,
    ): ResponseEntity<ResultResponse> {
        if (!user.isPrivate) throw PrivateException(ErrorCode.USER_NOT_PRIVATE)
        val miniProfiles = followRequestService.getFollowRequestUserList(user)
        return ResponseEntity.ok(ResultResponse.of(ResultCode.FOLLOW_REQUEST_LIST_SUCCESS, miniProfiles))
    }

    // 유저 팔로우 요청 조회
    @GetMapping("/{followerUserId}/request")
    fun retrieveUserFollowRequest(
        @AuthenticationPrincipal user: InstagramUser,
        @PathVariable("followerUserId") followerUserId: Long,
    ): ResponseEntity<ResultResponse> {
        if (!user.isPrivate) throw PrivateException(ErrorCode.USER_NOT_PRIVATE)
        if (user.id == followerUserId) throw UserHimselfException(ErrorCode.FOLLOWER_HIMSELF)
        val followRequestResponse = followRequestService.getUserFollowRequest(user, followerUserId)
        return ResponseEntity.ok(ResultResponse.of(ResultCode.GET_USER_FOLLOW_REQUEST_SUCCESS, followRequestResponse))
    }

    // 팔로우 요청 수락
    @PostMapping("/{followerUserId}/request")
    fun acceptFollowRequest(
        @AuthenticationPrincipal user: InstagramUser,
        @PathVariable("followerUserId") followerUserId: Long,
    ): ResponseEntity<ResultResponse> {
        if (!user.isPrivate) throw PrivateException(ErrorCode.USER_NOT_PRIVATE)
        if (user.id == followerUserId) throw UserHimselfException(ErrorCode.FOLLOWER_HIMSELF)
        val followResponse = followRequestService.postFollowRequest(user, followerUserId)
        return ResponseEntity.ok(ResultResponse.of(ResultCode.ACCEPT_FOLLOW_REQUEST_SUCCESS, followResponse))
    }

    // 팔로우 요청 거절
    @DeleteMapping("/{followerUserId}/request")
    fun declineFollowRequest(
        @AuthenticationPrincipal user: InstagramUser,
        @PathVariable("followerUserId") followerUserId: Long,
    ): ResponseEntity<ResultResponse> {
        if (!user.isPrivate) throw PrivateException(ErrorCode.USER_NOT_PRIVATE)
        if (user.id == followerUserId) throw UserHimselfException(ErrorCode.FOLLOWER_HIMSELF)
        followRequestService.removeFollowRequest(user, followerUserId)
        return ResponseEntity.ok(ResultResponse.of(ResultCode.DECLINE_FOLLOW_REQUEST_SUCCESS))
    }
}
