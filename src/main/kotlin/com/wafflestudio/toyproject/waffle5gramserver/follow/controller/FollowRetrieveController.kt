package com.wafflestudio.toyproject.waffle5gramserver.follow.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/friendship")
class FollowRetrieveController {

    //팔로워 여부 조회
    @GetMapping("/{followerUserId}/follower")
    fun retrieveFollower(

    ){
        TODO()
    }

    //유저 팔로우 여부 조회
    @GetMapping("/{userId}/follow")
    fun retrieveUserFollow(

    ){
        TODO()
    }

    //유저의 팔로워 및 현재 유저의 팔로잉 공통 목록 조회
    @GetMapping("/{userId}/follower/common")
    fun retrieveCommonUserBetweenUsersFollowerAndAuthUsersFollowing(

    ){
        TODO()
    }

    //유저의 팔로워 중 현재 유저의 팔로잉이 아닌 목록 조회
    @GetMapping("/{userId}/follower/diff")
    fun retrieveDifferenceBetweenUsersFollowerAndAuthUsersFollowing(

    ){
        TODO()
    }

    //유저의 팔로잉 및 현재 유저의 팔로잉 공통 목록 조회
    @GetMapping("/{userId}/following/common")
    fun retrieveCommonFollowingBetweenUserAndAuthUser(

    ){
        TODO()
    }

    //유저의 팔로잉 중 현재 유저의 팔로잉이 아닌 목록 조회
    @GetMapping("/{userId}/following/diff")
    fun retrieveDifferenceBetweenUsersFollowingAndAuthUsersFollowing(

    ){
        TODO()
    }
}