package com.wafflestudio.toyproject.waffle5gramserver.user.service

import com.wafflestudio.toyproject.waffle5gramserver.global.error_handling.ErrorCode
import com.wafflestudio.toyproject.waffle5gramserver.global.error_handling.global_exception.EntityNotFoundException
import com.wafflestudio.toyproject.waffle5gramserver.user.dto.UserPrivateResponse
import com.wafflestudio.toyproject.waffle5gramserver.user.exception.PrivateChangeFailException
import com.wafflestudio.toyproject.waffle5gramserver.user.repository.UserEntity
import com.wafflestudio.toyproject.waffle5gramserver.user.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class UserPrivateServiceImpl(
    private val userRepository: UserRepository,
) : UserPrivateService {

    override fun toPrivate(
        username: String,
        isPrivate: Boolean,
    ): UserPrivateResponse {
        if (isPrivate == true) { throw PrivateChangeFailException(ErrorCode.ALREADY_PRIVATE) } else { userRepository.updateIsPrivateByUsername(isPrivate = true, username = username) }
        val user: UserEntity = userRepository.findByUsername(username)
            .orElseThrow { EntityNotFoundException(ErrorCode.USER_NOT_FOUND) }
        return UserPrivateResponse(user.id, user.isPrivate)
    }

    override fun toOpen(
        username: String,
        isPrivate: Boolean,
    ): UserPrivateResponse {
        if (isPrivate == false) { throw PrivateChangeFailException(ErrorCode.ALREADY_OPEN) } else { userRepository.updateIsPrivateByUsername(isPrivate = false, username = username) }
        val user: UserEntity = userRepository.findByUsername(username)
            .orElseThrow { EntityNotFoundException(ErrorCode.USER_NOT_FOUND) }
        return UserPrivateResponse(user.id, user.isPrivate)
    }
}
