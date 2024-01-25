package com.wafflestudio.toyproject.waffle5gramserver.auth.service

import com.wafflestudio.toyproject.waffle5gramserver.auth.oauth2.CustomOAuth2User
import com.wafflestudio.toyproject.waffle5gramserver.auth.oauth2.FacebookOAuth2User
import com.wafflestudio.toyproject.waffle5gramserver.global.error_handling.ErrorCode
import com.wafflestudio.toyproject.waffle5gramserver.global.error_handling.global_exception.EntityAlreadyExistException
import com.wafflestudio.toyproject.waffle5gramserver.global.error_handling.global_exception.EntityNotFoundException
import com.wafflestudio.toyproject.waffle5gramserver.user.repository.FacebookUserEntity
import com.wafflestudio.toyproject.waffle5gramserver.user.repository.UserEntity
import com.wafflestudio.toyproject.waffle5gramserver.user.repository.UserRepository
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.security.oauth2.core.OAuth2AuthenticationException
import org.springframework.security.oauth2.core.OAuth2Error
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Service
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.annotation.Transactional
import org.springframework.transaction.support.TransactionTemplate
import java.util.Date
import kotlin.jvm.optionals.getOrNull

@Service
class CustomOAuth2UserService(
    private val userRepository: UserRepository,
    private val txManager: PlatformTransactionManager
) : DefaultOAuth2UserService(){

    private val txTemplate = TransactionTemplate(txManager)

    override fun loadUser(userRequest: OAuth2UserRequest) : OAuth2User {
        val oAuth2User: CustomOAuth2User = when (userRequest.clientRegistration.registrationId) {
            "facebook" -> FacebookOAuth2User(super.loadUser(userRequest).attributes, true, "")
            else -> throw OAuth2AuthenticationException(
                OAuth2Error("facebook"),
                "Invalid OAuth2 Provider"
            )
        }
        val userEntity = userRepository.findByFacebookId(oAuth2User.getId())
        if (userEntity == null) {
            registerFacebookOAuth2User(oAuth2User)
        } else {
            oAuth2User.username = userEntity.username
        }
        return oAuth2User
    }

    private fun registerFacebookOAuth2User(oAuth2User: CustomOAuth2User) {
        txTemplate.execute {
            val userEntity = userRepository.save(
                UserEntity(
                    username = "${oAuth2User.getProvider()}-${oAuth2User.getEmail()}",
                    name = oAuth2User.name
                )
            )
            userEntity.facebookUsers.add(
                FacebookUserEntity(
                    facebookId = oAuth2User.getId(),
                    user = userEntity
                )
            )
            oAuth2User.isExistingUser = false
            oAuth2User.username = userEntity.username
        }
    }

    /**
     * Update username and birthday after the first Facebook login
     * @return updated username
     */
    @Transactional
    fun updateFacebookOAuth2User(
        temporaryUsername: String,
        newUsername: String,
        birthday: Date
    ): String {
        if (userRepository.existsByUsername(newUsername)) {
            throw EntityAlreadyExistException(ErrorCode.USER_ALREADY_EXIST)
        }
        var userEntity = userRepository.findByUsername(temporaryUsername).getOrNull()
            ?: throw EntityNotFoundException(ErrorCode.USER_NOT_FOUND)
        userEntity.username = newUsername
        userEntity.birthday = birthday
        return userEntity.username
    }
}