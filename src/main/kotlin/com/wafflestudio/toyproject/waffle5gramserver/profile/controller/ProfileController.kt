package com.wafflestudio.toyproject.waffle5gramserver.profile.controller

import com.wafflestudio.toyproject.waffle5gramserver.auth.service.TokenRefreshService
import com.wafflestudio.toyproject.waffle5gramserver.profile.dto.BioRequest
import com.wafflestudio.toyproject.waffle5gramserver.profile.dto.FullProfileResponse
import com.wafflestudio.toyproject.waffle5gramserver.profile.dto.GenderRequest
import com.wafflestudio.toyproject.waffle5gramserver.profile.dto.NameRequest
import com.wafflestudio.toyproject.waffle5gramserver.profile.dto.NormalProfileResponse
import com.wafflestudio.toyproject.waffle5gramserver.profile.dto.ProfileImageResponse
import com.wafflestudio.toyproject.waffle5gramserver.profile.dto.UserLinkRequest
import com.wafflestudio.toyproject.waffle5gramserver.profile.dto.UserLinkResponse
import com.wafflestudio.toyproject.waffle5gramserver.profile.dto.UsernameRequest
import com.wafflestudio.toyproject.waffle5gramserver.profile.service.ProfileService
import com.wafflestudio.toyproject.waffle5gramserver.user.service.InstagramUser
import jakarta.servlet.http.HttpServletResponse
import jakarta.validation.Valid
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
@Validated
@RequestMapping("/api/v1/account")
class ProfileController(
    private val profileService: ProfileService,
    private val tokenRefreshService: TokenRefreshService
) {
    // 유저 프로필 조회
    @GetMapping("/{username}")
    fun retrieveUserProfile(
        @AuthenticationPrincipal authuser: InstagramUser,
        @PathVariable("username") username: String,
    ): ResponseEntity</*ResultResponse*/FullProfileResponse> {
        val fullProfileResponse = profileService.getUserProfile(authuser, username)
        // return ResponseEntity.ok(ResultResponse.of(ResultCode.RETRIEVE_PROFILE_SUCCESS,fullProfileResponse))
        return ResponseEntity.ok(fullProfileResponse)
    }

    // 프로필 사진 업로드
    @PostMapping(value = ["/profileEdit/image"], consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun postProfileImage(
        @AuthenticationPrincipal authuser: InstagramUser,
        @RequestPart("file") profileImage: MultipartFile,
    ): ResponseEntity</*ResultResponse*/ProfileImageResponse> {
        val profileImageResponse = profileService.uploadProfileImage(authuser, profileImage)
        // return ResponseEntity.ok(ResultResponse.of(ResultCode.POST_PROFILE_IMAGE_SUCCESS, profileImageResponse))
        return ResponseEntity.ok(profileImageResponse)
    }

    // 프로필 사진 삭제
    @DeleteMapping("/profileEdit/image")
    fun deleteProfileImage(
        @AuthenticationPrincipal authuser: InstagramUser,
    ): ResponseEntity</*ResultResponse*/ProfileImageResponse> {
        val profileImageResponse = profileService.deleteProfileImage(authuser)
        // url에 아무것도 넣지 않은 string으로 해서 반환
        // return ResponseEntity.ok(ResultResponse.of(ResultCode.DELETE_PROFILE_IMAGE_SUCCESS, profileImageResponse))
        return ResponseEntity.ok(profileImageResponse)
    }

    // 유저 이름 편집
    @PutMapping("/profileEdit/name")
    fun updateNameInProfile(
        @AuthenticationPrincipal authuser: InstagramUser,
        @Valid @RequestBody nameRequest: NameRequest,
    ): ResponseEntity</*ResultResponse*/NormalProfileResponse> {
        val normalProfileResponse = profileService.changeNameInProfile(authuser, nameRequest.name)
        // return ResponseEntity.ok(ResultResponse.of(ResultCode.UPDATE_NAME_SUCCESS, normalProfileResponse))
        return ResponseEntity.ok(normalProfileResponse)
    }

    // 유저 사용자이름 편집
    @PutMapping("/profileEdit/username")
    fun updateUsernameInProfile(
        @AuthenticationPrincipal authuser: InstagramUser,
        @Valid @RequestBody usernameRequest: UsernameRequest,
        response: HttpServletResponse
    ): ResponseEntity<Any> {
        val normalProfileResponse = profileService.changeUsernameInProfile(authuser, usernameRequest.username)
        val accessToken = tokenRefreshService.generateNewAccessToken(normalProfileResponse.username)
        val refreshToken = tokenRefreshService.generateNewRefreshToken(normalProfileResponse.username)
        tokenRefreshService.addRefreshTokenCookie(response, refreshToken, "/api/v1/auth/refresh_token")
        // return ResponseEntity.ok(ResultResponse.of(ResultCode.UPDATE_USERNAME_SUCCESS, normalProfileResponse))
        return ResponseEntity.ok(
            mapOf(
                "accessToken" to accessToken,
                "data" to normalProfileResponse
            )
        )
    }

    // 유저 소개 편집
    @PutMapping("/profileEdit/bio")
    fun updateBioInProfile(
        @AuthenticationPrincipal authuser: InstagramUser,
        @RequestBody bioRequest: BioRequest,
    ): ResponseEntity</*ResultResponse*/NormalProfileResponse> {
        val normalProfileResponse = profileService.changeBioInProfile(authuser, bioRequest.bio)
        // return ResponseEntity.ok(ResultResponse.of(ResultCode.UPDATE_BIO_SUCCESS, normalProfileResponse))
        return ResponseEntity.ok(normalProfileResponse)
    }

    // 유저 성별 편집
    @PutMapping("/profileEdit/gender")
    fun updatePronounInProfile(
        @AuthenticationPrincipal authuser: InstagramUser,
        @RequestBody genderRequest: GenderRequest,
    ): ResponseEntity</*ResultResponse*/NormalProfileResponse> {
        val normalProfileResponse = profileService.changeGenderInProfile(authuser, genderRequest.gender, genderRequest.isCustomGender)
        // return ResponseEntity.ok(ResultResponse.of(ResultCode.UPDATE_GENDER_SUCCESS, normalProfileResponse))
        return ResponseEntity.ok(normalProfileResponse)
    }

    // 유저 링크 추가
    @PostMapping("/profileEdit/link")
    fun addUserLinkInProfile(
        @AuthenticationPrincipal authuser: InstagramUser,
        @Valid @RequestBody userLinkRequest: UserLinkRequest,
    ): ResponseEntity</*ResultResponse*/UserLinkResponse> {
        val userLinkResponse = profileService.uploadUserLinkInProfile(authuser, userLinkRequest)
        // return ResponseEntity.ok(ResultResponse.of(ResultCode.POST_USER_LINK_SUCCESS, userLinkResponse))
        return ResponseEntity.ok(userLinkResponse)
    }

    // 유저 링크 삭제
    @DeleteMapping("/profileEdit/link/{linkId}")
    fun deleteUserLinkInProfile(
        @AuthenticationPrincipal authuser: InstagramUser,
        @PathVariable("linkId") linkId: Long,
    ): ResponseEntity</*ResultResponse*/UserLinkResponse> {
        val userLinkResponse = profileService.removeUserLinkInProfile(authuser, linkId)
        // return ResponseEntity.ok(ResultResponse.of(ResultCode.DELETE_USER_LINK_SUCCESS, userLinkResponse))
        return ResponseEntity.ok(userLinkResponse)
    }
}
