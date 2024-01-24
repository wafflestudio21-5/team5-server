package com.wafflestudio.toyproject.waffle5gramserver.profile.controller

import com.wafflestudio.toyproject.waffle5gramserver.profile.service.ProfileService
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/account")
class ProfileController(
    private val profileService: ProfileService,
){
    //유저 프로필 조회
    @GetMapping("/{userId}")
    fun retrieveUserProfile(

    ){

    }

    //프로필 사진 업로드
    @PostMapping("/profileEdit/image")
    fun postProfileImage(

    ){

    }

    //프로필 사진 삭제
    @DeleteMapping("/profileEdit/image")
    fun deleteProfileImage(

    ){

    }

    //유저 이름 편집
    @PutMapping("/profileEdit/name")
    fun updateNameInProfile(

    ){

    }

    //유저 사용자이름 편집
    @PutMapping("/profileEdit/username")
    fun updateUsernameInProfile(

    ){

    }

    //유저 소개 편집
    @PutMapping("/profileEdit/bio")
    fun updateBioInProfile(

    ){

    }

    //유저 성별 편집
    @PutMapping("/profileEdit/pronoun")
    fun updatePronounInProfile(

    ){

    }

    //유저 링크 추가
    @PostMapping("/profileEdit/link")
    fun addUserLinkInProfile(

    ){

    }

    //유저 링크 삭제
    @DeleteMapping("/profileEdit/link/{linkId}")
    fun deleteUserLinkInProfile(

    ){

    }
}
