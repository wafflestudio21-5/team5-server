package com.wafflestudio.toyproject.waffle5gramserver.user.service

import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service

@Service
class CurrentUserServiceImpl : CurrentUserService {

    override fun getUser(): InstagramUser {
        return SecurityContextHolder.getContext().authentication.principal as InstagramUser
    }
}
