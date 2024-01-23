package com.wafflestudio.toyproject.waffle5gramserver.auth.oauth2

import org.springframework.security.oauth2.core.user.OAuth2User

interface CustomOAuth2User: OAuth2User {
    var isExistingUser: Boolean
    fun getProvider(): String
    fun getId(): Long
    fun getEmail(): String
}
