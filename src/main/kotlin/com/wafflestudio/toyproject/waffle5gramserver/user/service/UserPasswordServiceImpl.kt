package com.wafflestudio.toyproject.waffle5gramserver.user.service

import com.wafflestudio.toyproject.waffle5gramserver.global.error_handling.BusinessException
import com.wafflestudio.toyproject.waffle5gramserver.global.error_handling.ErrorCode
import com.wafflestudio.toyproject.waffle5gramserver.user.repository.UserRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.support.TransactionTemplate
import kotlin.jvm.optionals.getOrNull

@Service
class UserPasswordServiceImpl(
    private val passwordEncoder: PasswordEncoder,
    private val userRepository: UserRepository,
    private val txManager: PlatformTransactionManager,
) : UserPasswordService {

    private val txTemplate = TransactionTemplate(txManager)

    override fun changePassword(rawOldPassword: String, rawNewPassword: String, user: InstagramUser) {
        if (rawOldPassword == rawNewPassword) {
            throw BusinessException(ErrorCode.NEW_PASSWORD_EQUAL)
        }
        val newPassword = passwordEncoder.encode(rawNewPassword)
        txTemplate.execute {
            val userEntity = userRepository.findById(user.id).getOrNull()
                ?: throw BusinessException(ErrorCode.USER_NOT_FOUND)
            if (!passwordEncoder.matches(rawOldPassword, userEntity.password)) {
                throw BusinessException(ErrorCode.INVALID_OLD_PASSWORD)
            }
            userEntity.password = newPassword
        }
    }
}
