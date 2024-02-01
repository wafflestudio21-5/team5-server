package com.wafflestudio.toyproject.waffle5gramserver.user.service

interface UserPasswordService {
    fun changePassword(rawOldPassword: String, rawNewPassword: String, user: InstagramUser)
}
