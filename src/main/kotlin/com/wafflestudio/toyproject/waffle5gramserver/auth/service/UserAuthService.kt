package com.wafflestudio.toyproject.waffle5gramserver.auth.service

import com.wafflestudio.toyproject.waffle5gramserver.user.repository.ContactType
import java.util.Date

interface UserAuthService {
    fun authenticateUsernamePassword(username: String, password: String)

    fun signUp(username: String, name: String, rawPassword: String, contact: String, contactType: ContactType, birthday: Date, isConfirmed: Boolean)
}
