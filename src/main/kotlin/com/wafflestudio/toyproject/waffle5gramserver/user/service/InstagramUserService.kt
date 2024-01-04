package com.wafflestudio.toyproject.waffle5gramserver.user.service

import com.wafflestudio.toyproject.waffle5gramserver.user.repository.UserRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class InstagramUserService(
    private val userRepository: UserRepository
): UserDetailsService {
    override fun loadUserByUsername(username: String?): UserDetails {
        if (username == null) {
            throw UsernameNotFoundException("User not found")
        }
        val userEntity = userRepository.findByUsername(username)
            ?: throw UsernameNotFoundException("User not found")
        return InstagramUser(
            id = userEntity.id,
            username = userEntity.username,
            name = userEntity.name,
            password = userEntity.password,
            birthday = userEntity.birthday,
            isPrivate = userEntity.isPrivate,
            createdAt = userEntity.createdAt,
            modifiedAt = userEntity.modifiedAt,
            pronoun = userEntity.pronoun,
            profileImageUrl = userEntity.profileImageUrl,
            bio = userEntity.bio
        )
    }
}