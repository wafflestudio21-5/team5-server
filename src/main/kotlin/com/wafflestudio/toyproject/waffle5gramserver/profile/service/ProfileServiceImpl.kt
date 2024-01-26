package com.wafflestudio.toyproject.waffle5gramserver.profile.service

import com.wafflestudio.toyproject.waffle5gramserver.follow.repository.FollowRepository
import com.wafflestudio.toyproject.waffle5gramserver.global.error_handling.ErrorCode
import com.wafflestudio.toyproject.waffle5gramserver.global.error_handling.global_exception.EntityNotFoundException
import com.wafflestudio.toyproject.waffle5gramserver.post.repository.PostRepository
import com.wafflestudio.toyproject.waffle5gramserver.profile.dto.FullProfileResponse
import com.wafflestudio.toyproject.waffle5gramserver.profile.dto.NormalProfileResponse
import com.wafflestudio.toyproject.waffle5gramserver.profile.dto.ProfileImageResponse
import com.wafflestudio.toyproject.waffle5gramserver.profile.dto.UserLinkRequest
import com.wafflestudio.toyproject.waffle5gramserver.profile.dto.UserLinkResponse
import com.wafflestudio.toyproject.waffle5gramserver.profile.exception.ProfileEditException
import com.wafflestudio.toyproject.waffle5gramserver.profile.mapper.ProfileResponseMapper
import com.wafflestudio.toyproject.waffle5gramserver.user.repository.UserLinkEntity
import com.wafflestudio.toyproject.waffle5gramserver.user.repository.UserLinkRepository
import com.wafflestudio.toyproject.waffle5gramserver.user.repository.UserRepository
import com.wafflestudio.toyproject.waffle5gramserver.user.service.InstagramUser
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class ProfileServiceImpl(
    private val userRepository: UserRepository,
    private val userLinkRepository: UserLinkRepository,
    private val followRepository: FollowRepository,
    private val postRepository: PostRepository,
) : ProfileService {
    @Transactional
    override fun getUserProfile(
        authuser: InstagramUser,
        username: String,
    ): FullProfileResponse {
        val profileUser = userRepository.findByUsername(username)
            .orElseThrow { EntityNotFoundException(ErrorCode.USER_NOT_FOUND) }
        val post = postRepository.findAllByUserId(profileUser.id)
        val following = followRepository.findAllByFollowerUserId(profileUser.id)
        val follower = followRepository.findAllByFolloweeUserId(profileUser.id)

        val postNumber = post.count().toLong()
        val followingNumber = following.count().toLong()
        val followerNumber = follower.count().toLong()

        return ProfileResponseMapper.toFullProfileResponse(profileUser, postNumber, followingNumber, followerNumber)
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
        userRepository.updateNameById(authuser.id, changeName)
        val user = userRepository.findById(authuser.id)
            .orElseThrow { EntityNotFoundException(ErrorCode.USER_NOT_FOUND) }
        return ProfileResponseMapper.toNormalProfileResponse(user)
    }

    @Transactional
    override fun changeUsernameInProfile(
        authuser: InstagramUser,
        changeUsername: String
    ): NormalProfileResponse {
        if (!userRepository.findByUsername(changeUsername).isEmpty) throw ProfileEditException(ErrorCode.USERNAME_ALREADY_EXIST)
        userRepository.updateUsernameById(authuser.id, changeUsername)
        val user = userRepository.findById(authuser.id)
            .orElseThrow { EntityNotFoundException(ErrorCode.USER_NOT_FOUND) }
        return ProfileResponseMapper.toNormalProfileResponse(user)
    }

    @Transactional
    override fun changeBioInProfile(
        authuser: InstagramUser,
        changeBio: String?
    ): NormalProfileResponse {
        userRepository.updateBioById(authuser.id, changeBio)
        val user = userRepository.findById(authuser.id)
            .orElseThrow { EntityNotFoundException(ErrorCode.USER_NOT_FOUND) }
        return ProfileResponseMapper.toNormalProfileResponse(user)
    }

    @Transactional
    override fun changeGenderInProfile(
        authuser: InstagramUser,
        gender: String?,
        isCustomGender: Boolean
    ): NormalProfileResponse {
        userRepository.updateGenderAndIsCustomGenderById(authuser.id, gender, isCustomGender)
        val user = userRepository.findById(authuser.id)
            .orElseThrow { EntityNotFoundException(ErrorCode.USER_NOT_FOUND) }
        return ProfileResponseMapper.toNormalProfileResponse(user)
    }

    @Transactional
    override fun uploadUserLinkInProfile(
        authuser: InstagramUser,
        userLinkRequest: UserLinkRequest
    ): UserLinkResponse {
        val user = userRepository.findById(authuser.id)
            .orElseThrow { EntityNotFoundException(ErrorCode.USER_NOT_FOUND) }
        val userLinkEntity = UserLinkEntity(
            user = user,
            link = userLinkRequest.userLinks.link,
            linkTitle = userLinkRequest.userLinks.linkTitle
        )
        userLinkRepository.save(userLinkEntity)

        return UserLinkResponse(
            userLinks = userLinkRepository.findAllByUserId(authuser.id)
                .map { ProfileResponseMapper.userLinkEntityToDTO(it) }
                .toMutableList()
        )
    }

    @Transactional
    override fun removeUserLinkInProfile(
        authuser: InstagramUser,
        linkId: Long
    ): UserLinkResponse {
        val link = userLinkRepository.findById(linkId)
            .orElseThrow { EntityNotFoundException(ErrorCode.LINK_NOT_FOUND) }
        userLinkRepository.delete(link)
        return UserLinkResponse(
            userLinks = userLinkRepository.findAllByUserId(authuser.id)
                .map { ProfileResponseMapper.userLinkEntityToDTO(it) }
                .toMutableList()
        )
    }
}
