package com.wafflestudio.toyproject.waffle5gramserver.user.controller

import com.wafflestudio.toyproject.waffle5gramserver.global.result_handling.ResultCode
import com.wafflestudio.toyproject.waffle5gramserver.global.result_handling.ResultResponse
import com.wafflestudio.toyproject.waffle5gramserver.user.dto.UserPrivateResponse
import com.wafflestudio.toyproject.waffle5gramserver.user.service.InstagramUser
import com.wafflestudio.toyproject.waffle5gramserver.user.service.UserPrivateService
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/account")
class UserPrivateController(
    private val userPrivateService: UserPrivateService,
) {
    @PutMapping("/toPrivate")
    fun toPrivate(
        @AuthenticationPrincipal user: InstagramUser,
    ): ResponseEntity<ResultResponse> {
        val userPrivateResponse: UserPrivateResponse = userPrivateService.toPrivate(user.id, user.isPrivate)
        return ResponseEntity.ok(ResultResponse.of(ResultCode.TO_PRIVATE_CHANGE_SUCCESS, userPrivateResponse))
    }

    @PutMapping("/toOpen")
    fun toOpen(
        @AuthenticationPrincipal user: InstagramUser,
    ): ResponseEntity<ResultResponse> {
        val userPrivateResponse: UserPrivateResponse = userPrivateService.toOpen(user.id, user.isPrivate)
        return ResponseEntity.ok(ResultResponse.of(ResultCode.TO_OPEN_CHANGE_SUCCESS, userPrivateResponse))
    }
}
