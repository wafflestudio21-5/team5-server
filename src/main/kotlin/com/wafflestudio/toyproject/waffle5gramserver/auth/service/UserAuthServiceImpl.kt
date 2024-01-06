package com.wafflestudio.toyproject.waffle5gramserver.auth.service

import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service

@Service
class UserAuthServiceImpl(
    private val authenticationManager: AuthenticationManager
) : UserAuthService {

    override fun authenticateUsernamePassword(username: String, password: String) {
        val authentication = authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(username, password)
        )
        SecurityContextHolder.getContext().authentication = authentication
    }
}
