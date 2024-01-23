package com.wafflestudio.toyproject.waffle5gramserver.auth.controller

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class FacebookLoginController {

    @GetMapping("/api/v1/auth/facebook_login")
    fun login(request: HttpServletRequest, response: HttpServletResponse) {
        response.sendRedirect("/oauth2/authorization/facebook")
    }
}