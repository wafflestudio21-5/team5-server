package com.wafflestudio.toyproject.waffle5gramserver.user.controller

import com.wafflestudio.toyproject.waffle5gramserver.user.dto.PasswordChangeRequestDto
import com.wafflestudio.toyproject.waffle5gramserver.user.service.InstagramUser
import com.wafflestudio.toyproject.waffle5gramserver.user.service.UserPasswordService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class UserPasswordController(
    private val userPasswordService: UserPasswordService
) {
    @PutMapping("/api/v1/account/password")
    fun updatePassword(
        @RequestBody passwordChangeRequestDto: PasswordChangeRequestDto,
        @AuthenticationPrincipal user: InstagramUser
    ): ResponseEntity<Any?> {
        userPasswordService.changePassword(
            rawOldPassword = passwordChangeRequestDto.oldPassword,
            rawNewPassword = passwordChangeRequestDto.newPassword,
            user = user
        )
        return ResponseEntity.status(HttpStatus.OK).build()
    }
}
