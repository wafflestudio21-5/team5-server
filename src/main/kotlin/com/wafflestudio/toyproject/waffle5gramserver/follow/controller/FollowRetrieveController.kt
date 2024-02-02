package com.wafflestudio.toyproject.waffle5gramserver.follow.controller

import com.wafflestudio.toyproject.waffle5gramserver.follow.dto.DiffFollowResponse
import com.wafflestudio.toyproject.waffle5gramserver.follow.dto.IsRequestMiniProfile
import com.wafflestudio.toyproject.waffle5gramserver.follow.exception.UserHimselfException
import com.wafflestudio.toyproject.waffle5gramserver.follow.service.FollowRetrieveService
import com.wafflestudio.toyproject.waffle5gramserver.global.error_handling.ErrorCode
import com.wafflestudio.toyproject.waffle5gramserver.global.result_handling.ResultCode
import com.wafflestudio.toyproject.waffle5gramserver.global.result_handling.ResultResponse
import com.wafflestudio.toyproject.waffle5gramserver.user.service.InstagramUser
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/friendship")
class FollowRetrieveController(
    private val followRetrieveService: FollowRetrieveService,
) {

    // 팔로워 여부 조회
    @GetMapping("/{followerUsername}/follower")
    fun retrieveFollower(
        @AuthenticationPrincipal user: InstagramUser,
        @PathVariable("followerUsername") followerUsername: String,
    ): ResponseEntity<ResultResponse> {
        if (user.username == followerUsername) throw UserHimselfException(ErrorCode.FOLLOWER_HIMSELF)
        followRetrieveService.getFollower(user, followerUsername)
        return ResponseEntity.ok(ResultResponse.of(ResultCode.RETRIEVE_FOLLOWER_SUCCESS))
    }

    // 유저 팔로우 여부 조회
    @GetMapping("/{username}/follow")
    fun retrieveUserFollow(
        @AuthenticationPrincipal user: InstagramUser,
        @PathVariable("username") username: String,
    ): ResponseEntity<ResultResponse> {
        if (user.username == username) throw UserHimselfException(ErrorCode.FOLLOWER_HIMSELF)
        followRetrieveService.getUserFollow(user, username)
        return ResponseEntity.ok(ResultResponse.of(ResultCode.GET_USER_FOLLOW_SUCCESS))
    }

    // 유저의 팔로워 및 현재 유저의 팔로잉 공통 목록 조회
    @GetMapping("/{username}/follower/common")
    fun retrieveCommonUserBetweenUsersFollowerAndAuthUsersFollowing(
        @AuthenticationPrincipal user: InstagramUser,
        @PathVariable("username") username: String,
    ): ResponseEntity</*ResultResponse*/DiffFollowResponse> {
        val commonFollowResponse = followRetrieveService.getCommonUserBetweenUsersFollowerAndAuthUsersFollowing(user, username)
        // return ResponseEntity.ok(ResultResponse.of(ResultCode.GET_COMMON_FOLLOWER_FOLLOWING, commonFollowResponse))
        return ResponseEntity.ok(
            DiffFollowResponse(
                count = commonFollowResponse.count,
                miniProfiles = commonFollowResponse.miniProfiles.map {
                    IsRequestMiniProfile(
                        userId = it.userId,
                        username = it.username,
                        name = it.name,
                        profileImageUrl = it.profileImageUrl,
                        isRequest = false,
                    )
                }.toMutableList()
            )
        )
    }

    // 유저의 팔로워 중 현재 유저의 팔로잉이 아닌 목록 조회
    @GetMapping("/{username}/follower/diff")
    fun retrieveDifferenceBetweenUsersFollowerAndAuthUsersFollowing(
        @AuthenticationPrincipal user: InstagramUser,
        @PathVariable("username") username: String,
    ): ResponseEntity</*ResultResponse*/DiffFollowResponse> {
        val diffFollowResponse = followRetrieveService.getDifferenceBetweenUsersFollowerAndAuthUsersFollowing(user, username)
        // return ResponseEntity.ok(ResultResponse.of(ResultCode.GET_DIFF_FOLLOWER_FOLLOWING, diffFollowResponse))
        return ResponseEntity.ok(diffFollowResponse)
    }

    // 유저의 팔로잉 및 현재 유저의 팔로잉 공통 목록 조회
    @GetMapping("/{username}/following/common")
    fun retrieveCommonFollowingBetweenUserAndAuthUser(
        @AuthenticationPrincipal user: InstagramUser,
        @PathVariable("username") username: String,
    ): ResponseEntity</*ResultResponse*/DiffFollowResponse> {
        val commonFollowResponse = followRetrieveService.getCommonFollowingBetweenUserAndAuthUser(user, username)
        // return ResponseEntity.ok(ResultResponse.of(ResultCode.GET_COMMON_FOLLOWING_FOLLOWING, commonFollowResponse))
        return ResponseEntity.ok(
            DiffFollowResponse(
                count = commonFollowResponse.count,
                miniProfiles = commonFollowResponse.miniProfiles.map {
                    IsRequestMiniProfile(
                        userId = it.userId,
                        username = it.username,
                        name = it.name,
                        profileImageUrl = it.profileImageUrl,
                        isRequest = false,
                    )
                }.toMutableList()
            )
        )
    }

    // 유저의 팔로잉 중 현재 유저의 팔로잉이 아닌 목록 조회
    @GetMapping("/{username}/following/diff")
    fun retrieveDifferenceBetweenUsersFollowingAndAuthUsersFollowing(
        @AuthenticationPrincipal user: InstagramUser,
        @PathVariable("username") username: String,
    ): ResponseEntity</*ResultResponse*/DiffFollowResponse> {
        val diffFollowResponse = followRetrieveService.getDifferenceBetweenUsersFollowingAndAuthUsersFollowing(user, username)
        // return ResponseEntity.ok(ResultResponse.of(ResultCode.GET_DIFF_FOLLOWING_FOLLOWING, diffFollowResponse))
        return ResponseEntity.ok(diffFollowResponse)
    }
}
