package com.wafflestudio.toyproject.waffle5gramserver.user.service

import com.wafflestudio.toyproject.waffle5gramserver.global.error_handling.ErrorCode
import com.wafflestudio.toyproject.waffle5gramserver.global.error_handling.global_exception.EntityNotFoundException
import com.wafflestudio.toyproject.waffle5gramserver.user.dto.UserPrivateResponse
import com.wafflestudio.toyproject.waffle5gramserver.user.exception.PrivateChangeFailException
import com.wafflestudio.toyproject.waffle5gramserver.user.repository.UserEntity
import com.wafflestudio.toyproject.waffle5gramserver.user.repository.UserRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class UserPrivateServiceImpl(
    private val userRepository: UserRepository,
) : UserPrivateService {

    @Transactional
    override fun toPrivate(
        userId: Long,
        isPrivate: Boolean,
    ): UserPrivateResponse {
        if (isPrivate == true) { throw PrivateChangeFailException(ErrorCode.ALREADY_PRIVATE) }
        val user: UserEntity = userRepository.findById(userId)
            .orElseThrow { EntityNotFoundException(ErrorCode.USER_NOT_FOUND) }
        userRepository.updateIsPrivateById(isPrivate = true, Id = userId)
        return UserPrivateResponse(user.id, true)
    }

    @Transactional
    override fun toOpen(
        userId: Long,
        isPrivate: Boolean,
    ): UserPrivateResponse {
        if (isPrivate == false) { throw PrivateChangeFailException(ErrorCode.ALREADY_OPEN) }
        val user: UserEntity = userRepository.findById(userId)
            .orElseThrow { EntityNotFoundException(ErrorCode.USER_NOT_FOUND) }
        userRepository.updateIsPrivateById(isPrivate = false, Id = userId)
        return UserPrivateResponse(user.id, false)
    }
}
