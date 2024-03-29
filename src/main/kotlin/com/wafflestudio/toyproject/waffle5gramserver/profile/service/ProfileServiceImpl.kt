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
import com.wafflestudio.toyproject.waffle5gramserver.utils.S3ImageUpload
import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

@Service
class ProfileServiceImpl(
    private val userRepository: UserRepository,
    private val userLinkRepository: UserLinkRepository,
    private val followRepository: FollowRepository,
    private val postRepository: PostRepository,
    private val s3ImageUpload: S3ImageUpload,
    @Qualifier("allowedImageTypes")
    private val allowedImageTypes: List<String>
) : ProfileService {
    private final val defaultImageUrl = "https://waffle5grambucket.s3.ap-northeast-2.amazonaws.com/7506b932-5c8f-4f7c-9a15-6f78f1cc7b49-Default_Profile.png"

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
        profileImage: MultipartFile
    ): ProfileImageResponse {
        val user = userRepository.findById(authuser.id).orElseThrow { EntityNotFoundException(ErrorCode.USER_NOT_FOUND) }
        val contentType = profileImage.contentType ?: throw ProfileEditException(ErrorCode.FILE_CONVERT_FAIL)
        if (!allowedImageTypes.contains(contentType)) {
            throw ProfileEditException(ErrorCode.INVALID_IMAGE_TYPE)
        }
        try {
            val profileImageUrl = s3ImageUpload.uploadImage(profileImage)
            userRepository.updateProfileImageUrlById(user.id, profileImageUrl)
            return ProfileImageResponse(profileImageUrl)
        } catch (e: Exception) {
            throw ProfileEditException(ErrorCode.S3_UPLOAD_ERROR)
        }
    }

    @Transactional
    override fun deleteProfileImage(
        authuser: InstagramUser
    ): ProfileImageResponse {
        val user = userRepository.findById(authuser.id)
            .orElseThrow { EntityNotFoundException(ErrorCode.USER_NOT_FOUND) }
        val deprecatedUrl = user.profileImageUrl
        try {
            if (deprecatedUrl != null) {
                s3ImageUpload.deleteImage(deprecatedUrl)
            }
            val profileImageUrl = defaultImageUrl
            userRepository.updateProfileImageUrlById(authuser.id, profileImageUrl)
            return ProfileImageResponse(profileImageUrl)
        } catch (e: Exception) {
            throw ProfileEditException(ErrorCode.S3_DELETE_ERROR)
        }
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
    override fun changeUserLinkInProfile(
        authuser: InstagramUser,
        userLinkRequest: UserLinkRequest,
        linkId: Long,
    ): UserLinkResponse {
        userLinkRepository.updateUserLinkById(
            authuser.id,
            linkId,
            userLinkRequest.userLinks.linkTitle,
            userLinkRequest.userLinks.link
        )
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
