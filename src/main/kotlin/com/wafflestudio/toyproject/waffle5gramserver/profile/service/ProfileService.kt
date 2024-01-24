package com.wafflestudio.toyproject.waffle5gramserver.profile.service

import com.wafflestudio.toyproject.waffle5gramserver.profile.dto.FullProfileResponse
import com.wafflestudio.toyproject.waffle5gramserver.profile.dto.NormalProfileResponse
import com.wafflestudio.toyproject.waffle5gramserver.profile.dto.ProfileImageResponse
import com.wafflestudio.toyproject.waffle5gramserver.profile.dto.UserLinkRequest
import com.wafflestudio.toyproject.waffle5gramserver.profile.dto.UserLinkResponse
import com.wafflestudio.toyproject.waffle5gramserver.user.service.InstagramUser

interface ProfileService{
    fun getUserProfile(authuser: InstagramUser, userId: Long) : FullProfileResponse

    fun uploadProfileImage(authuser: InstagramUser, profileImageUrl: String) : ProfileImageResponse

    fun deleteProfileImage(authuser: InstagramUser) : ProfileImageResponse

    fun changeNameInProfile(authuser: InstagramUser, changeName: String) : NormalProfileResponse

    fun changeUsernameInProfile(authuser: InstagramUser, changeUsername: String) : NormalProfileResponse

    fun changeBioInProfile(authuser: InstagramUser, changeBio: String?) : NormalProfileResponse

    fun changeGenderInProfile(authuser: InstagramUser, gender: String?, isCustomGender: Boolean) : NormalProfileResponse

    fun uploadUserLinkInProfile(authuser: InstagramUser, userLinkRequest: UserLinkRequest) : UserLinkResponse

    fun removeUserLinkInProfile(authuser: InstagramUser, linkId: Long) : UserLinkResponse
}
