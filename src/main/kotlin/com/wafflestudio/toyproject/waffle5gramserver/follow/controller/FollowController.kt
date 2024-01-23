package com.wafflestudio.toyproject.waffle5gramserver.follow.controller

import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/friendship")
class FollowController {

    //공개 유저 팔로우
    @PostMapping("/{userId}/follow")
    fun followNonPrivateUser(

    ){
        TODO()
    }

    //유저 언팔로우
    @DeleteMapping("/{userId}/follow")
    fun unfollowUser(

    ){
        TODO()
    }

    //팔로워 삭제
    @DeleteMapping("/{followerUserId}/follower")
    fun deleteFollower(

    ){
        TODO()
    }
}