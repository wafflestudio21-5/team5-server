package com.wafflestudio.toyproject.waffle5gramserver.auth.service

import com.wafflestudio.toyproject.waffle5gramserver.auth.oauth2.CustomOAuth2User
import com.wafflestudio.toyproject.waffle5gramserver.auth.oauth2.FacebookOAuth2User
import com.wafflestudio.toyproject.waffle5gramserver.user.repository.FacebookUserEntity
import com.wafflestudio.toyproject.waffle5gramserver.user.repository.UserEntity
import com.wafflestudio.toyproject.waffle5gramserver.user.repository.UserRepository
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.security.oauth2.core.OAuth2AuthenticationException
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CustomOAuth2UserService(
    private val userRepository: UserRepository
) : DefaultOAuth2UserService(){

    override fun loadUser(userRequest: OAuth2UserRequest) : OAuth2User {
        val oAuth2User: CustomOAuth2User = when (userRequest.clientRegistration.registrationId) {
            "facebook" -> FacebookOAuth2User(super.loadUser(userRequest).attributes, true)
            else -> throw OAuth2AuthenticationException("Invalid OAuth2 Provider")
        }
        val userEntity = userRepository.findByFacebookId(oAuth2User.getId())
        if (userEntity == null) {
            registerOAuth2User(oAuth2User)
        }
        return oAuth2User
    }

    @Transactional
    fun registerOAuth2User(oAuth2User: CustomOAuth2User) {
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
    }
}