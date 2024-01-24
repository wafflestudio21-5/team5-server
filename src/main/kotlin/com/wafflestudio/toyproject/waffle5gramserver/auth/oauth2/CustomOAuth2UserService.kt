package com.wafflestudio.toyproject.waffle5gramserver.auth.oauth2

import com.wafflestudio.toyproject.waffle5gramserver.user.repository.FacebookUserEntity
import com.wafflestudio.toyproject.waffle5gramserver.user.repository.UserEntity
import com.wafflestudio.toyproject.waffle5gramserver.user.repository.UserRepository
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.security.oauth2.core.OAuth2AuthenticationException
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Service
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.support.TransactionTemplate

@Service
class CustomOAuth2UserService(
    private val userRepository: UserRepository,
    private val txManager: PlatformTransactionManager
) : DefaultOAuth2UserService(){

    private val txTemplate = TransactionTemplate(txManager)

    override fun loadUser(userRequest: OAuth2UserRequest) : OAuth2User {
        val oAuth2User: CustomOAuth2User = when (userRequest.clientRegistration.registrationId) {
            "facebook" -> FacebookOAuth2User(super.loadUser(userRequest).attributes, true, "")
            else -> throw OAuth2AuthenticationException("Invalid OAuth2 Provider")
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
}