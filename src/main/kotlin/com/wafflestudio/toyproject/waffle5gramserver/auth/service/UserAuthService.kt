package com.wafflestudio.toyproject.waffle5gramserver.auth.service

interface UserAuthService {
    fun authenticateUsernamePassword(username: String, password: String)
}
