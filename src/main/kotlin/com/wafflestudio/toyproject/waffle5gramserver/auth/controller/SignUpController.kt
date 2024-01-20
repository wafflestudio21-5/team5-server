package com.wafflestudio.toyproject.waffle5gramserver.auth.controller

import com.wafflestudio.toyproject.waffle5gramserver.auth.dto.SignUpRequestDto
import com.wafflestudio.toyproject.waffle5gramserver.auth.service.UserAuthService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class SignUpController(
    private val userAuthService: UserAuthService
) {

    @PostMapping("/api/v1/auth/signup")
    fun signUp(@RequestBody @Valid signUpRequestDto: SignUpRequestDto): ResponseEntity<Any?> {
        userAuthService.signUp(
            username = signUpRequestDto.username,
            name = signUpRequestDto.name,
            rawPassword = signUpRequestDto.password,
            contact = signUpRequestDto.contact,
            contactType = signUpRequestDto.contactType,
            birthday = signUpRequestDto.birthday,
            isConfirmed = false
        )
        return ResponseEntity.status(HttpStatus.CREATED).build()
    }
}
