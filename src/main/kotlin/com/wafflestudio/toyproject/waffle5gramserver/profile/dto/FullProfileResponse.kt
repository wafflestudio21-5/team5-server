package com.wafflestudio.toyproject.waffle5gramserver.profile.dto

import java.util.Date

data class FullProfileResponse(
    val userId: Long,
    val username: String,
    val name: String,
    val birthday: Date?,
    val isPrivate: Boolean,
    val gender: String?,
    val isCustomGender: Boolean,
    val profileImageUrl: String?,
    val bio: String?,
    val userLinks: MutableList<UserLinkdto>,
    val contacts: MutableList<Contactdto>,
    val postNumber: Long,
    val followingNumber: Long,
    val followerNumber: Long,
)
