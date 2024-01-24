package com.wafflestudio.toyproject.waffle5gramserver.auth.service

import com.wafflestudio.toyproject.waffle5gramserver.global.error_handling.ErrorCode
import com.wafflestudio.toyproject.waffle5gramserver.global.error_handling.global_exception.EntityAlreadyExistException
import com.wafflestudio.toyproject.waffle5gramserver.user.repository.ContactEntity
import com.wafflestudio.toyproject.waffle5gramserver.user.repository.ContactType
import com.wafflestudio.toyproject.waffle5gramserver.user.repository.UserEntity
import com.wafflestudio.toyproject.waffle5gramserver.user.repository.UserRepository
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.Date

@Service
class UserAuthServiceImpl(
    private val authenticationManager: AuthenticationManager,
    private val passwordEncoder: PasswordEncoder,
    private val userRepository: UserRepository
) : UserAuthService {

    override fun authenticateUsernamePassword(username: String, password: String) {
        val authentication = authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(username, password)
        )
        SecurityContextHolder.getContext().authentication = authentication
    }

    @Transactional
    override fun signUp(
        username: String,
        name: String,
        rawPassword: String,
        contact: String,
        contactType: ContactType,
        birthday: Date,
        isConfirmed: Boolean
    ) {
        if (userRepository.existsByUsername(username)) {
            throw EntityAlreadyExistException(ErrorCode.USER_ALREADY_EXIST)
        }
        var userEntity = UserEntity(
            username = username,
            name = name,
            password = passwordEncoder.encode(rawPassword),
            birthday = birthday,
            isPrivate = false,
            gender = null,
            isCustomGender = false,
            profileImageUrl = null,
            bio = null
        )
        userEntity = userRepository.save(userEntity)
        userEntity.contacts.add(
            ContactEntity(
                user = userEntity,
                contactType = contactType,
                contactValue = contact,
                isConfirmed = isConfirmed
            )
        )
    }
}
