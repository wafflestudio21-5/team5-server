package com.wafflestudio.toyproject.waffle5gramserver.profile.service

import com.wafflestudio.toyproject.waffle5gramserver.profile.dto.FullProfileResponse
import com.wafflestudio.toyproject.waffle5gramserver.profile.dto.NormalProfileResponse
import com.wafflestudio.toyproject.waffle5gramserver.profile.dto.ProfileImageResponse
import com.wafflestudio.toyproject.waffle5gramserver.profile.dto.UserLinkRequest
import com.wafflestudio.toyproject.waffle5gramserver.profile.dto.UserLinkResponse
import com.wafflestudio.toyproject.waffle5gramserver.user.repository.UserLinkRepository
import com.wafflestudio.toyproject.waffle5gramserver.user.repository.UserRepository
import com.wafflestudio.toyproject.waffle5gramserver.user.service.InstagramUser
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class ProfileServiceImpl(
    private val userRepository: UserRepository,
    private val userLinkRepository: UserLinkRepository,
) : ProfileService {
    @Transactional
    override fun getUserProfile(
        authuser: InstagramUser,
        userId: Long
    ): FullProfileResponse {
        TODO("Not yet implemented")
    }

    @Transactional
    override fun uploadProfileImage(
        authuser: InstagramUser,
        profileImageUrl: String
    ): ProfileImageResponse {
        TODO("Not yet implemented")
    }

    @Transactional
    override fun deleteProfileImage(
        authuser: InstagramUser
    ): ProfileImageResponse {
        TODO("Not yet implemented")
    }

    @Transactional
    override fun changeNameInProfile(
        authuser: InstagramUser,
        changeName: String
    ): NormalProfileResponse {
        TODO("Not yet implemented")
    }

    @Transactional
    override fun changeUsernameInProfile(
        authuser: InstagramUser,
        changeUsername: String
    ): NormalProfileResponse {
        TODO("Not yet implemented")
    }

    @Transactional
    override fun changeBioInProfile(
        authuser: InstagramUser,
        changeBio: String?
    ): NormalProfileResponse {
        TODO("Not yet implemented")
    }

    @Transactional
    override fun changeGenderInProfile(
        authuser: InstagramUser,
        gender: String?,
        isCustomGender: Boolean
    ): NormalProfileResponse {
        TODO("Not yet implemented")
    }

    @Transactional
    override fun uploadUserLinkInProfile(
        authuser: InstagramUser,
        userLinkRequest: UserLinkRequest
    ): UserLinkResponse {
        TODO("Not yet implemented")
    }

    @Transactional
    override fun removeUserLinkInProfile(
        authuser: InstagramUser,
        linkId: Long
    ): UserLinkResponse {
        TODO("Not yet implemented")
    }
}
