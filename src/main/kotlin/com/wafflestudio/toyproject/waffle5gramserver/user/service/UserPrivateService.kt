package com.wafflestudio.toyproject.waffle5gramserver.user.service

import com.wafflestudio.toyproject.waffle5gramserver.user.dto.UserPrivateResponse

interface UserPrivateService {
    fun toPrivate(username: String, isPrivate: Boolean): UserPrivateResponse

    fun toOpen(username: String, isPrivate: Boolean): UserPrivateResponse
}
