package com.wafflestudio.toyproject.waffle5gramserver.user.service

import com.wafflestudio.toyproject.waffle5gramserver.user.dto.UserPrivateResponse

interface UserPrivateService {
    fun toPrivate(userId: Long, isPrivate: Boolean): UserPrivateResponse

    fun toOpen(userId: Long, isPrivate: Boolean): UserPrivateResponse
}
