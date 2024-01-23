package com.wafflestudio.toyproject.waffle5gramserver.follow.controller

import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/friendship")
class FollowRequestController(

) {
    //비공개 유저 팔로우 요청 조회
    @GetMapping("/{userId}/follow/request")
    fun retrieveFollowRequestToPrivateUser(

    ){
        TODO()
    }

    //비공개 유저 팔로우 요청
    @PostMapping("/{userId}/follow/request")
    fun requestFollowToPrivateUser(

    ){
        TODO()
    }

    //비공개 유저 팔로우 요청 취소
    @DeleteMapping("/{userId}/follow/request")
    fun deleteFollowRequesttoPrivateUser(

    ){
        TODO()
    }

    //팔로우 요청 유저 목록 조회
    @GetMapping("/requestlist")
    fun retrieveFollowRequestUserList(

    ){
        TODO()
    }

    //유저 팔로우 요청 조회
    @GetMapping("/{followerUserId}/request")
    fun retrieveUserFollowRequest(

    ){
        TODO()
    }

    //팔로우 요청 수락
    @PostMapping("/{followerUserId}/request")
    fun acceptFollowRequest(

    ){
        TODO()
    }

    //팔로우 요청 거절
    @DeleteMapping("/{followerUserId}/request")
    fun declineFollowRequest(

    ){
        TODO()
    }
}