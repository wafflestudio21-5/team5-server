package com.wafflestudio.toyproject.waffle5gramserver.profile.mapper

import com.wafflestudio.toyproject.waffle5gramserver.profile.dto.Contactdto
import com.wafflestudio.toyproject.waffle5gramserver.profile.dto.FullProfileResponse
import com.wafflestudio.toyproject.waffle5gramserver.profile.dto.NormalProfileResponse
import com.wafflestudio.toyproject.waffle5gramserver.profile.dto.UserLinkdto
import com.wafflestudio.toyproject.waffle5gramserver.user.repository.ContactEntity
import com.wafflestudio.toyproject.waffle5gramserver.user.repository.UserEntity
import com.wafflestudio.toyproject.waffle5gramserver.user.repository.UserLinkEntity

class ProfileResponseMapper {
    companion object {
        fun toFullProfileResponse(
            user: UserEntity,
            postNumber: Long,
            followingNumber: Long,
            followerNumber: Long,
        ): FullProfileResponse {
            return FullProfileResponse(
                userId = user.id,
                username = user.username,
                name = user.name,
                birthday = user.birthday,
                isPrivate = user.isPrivate,
                gender = user.gender,
                isCustomGender = user.isCustomGender,
                profileImageUrl = user.profileImageUrl,
                bio = user.bio,
                userLinks = user.userLinks.map { userLinkEntityToDTO(it) }.toMutableList(),
                contacts = user.contacts.map { contactEntityToDTO(it) }.toMutableList(),
                postNumber = postNumber,
                followingNumber = followingNumber,
                followerNumber = followerNumber
            )
        }

        fun toNormalProfileResponse(
            user: UserEntity,
        ): NormalProfileResponse {
            return NormalProfileResponse(
                userId = user.id,
                username = user.username,
                name = user.name,
                birthday = user.birthday,
                isPrivate = user.isPrivate,
                gender = user.gender,
                isCustomGender = user.isCustomGender,
                profileImageUrl = user.profileImageUrl,
                bio = user.bio,
                userLinks = user.userLinks.map { userLinkEntityToDTO(it) }.toMutableList(),
                contacts = user.contacts.map { contactEntityToDTO(it) }.toMutableList(),
            )
        }

        fun userLinkEntityToDTO(
            userLink: UserLinkEntity,
        ): UserLinkdto {
            return UserLinkdto(
                linkId = userLink.id,
                linkTitle = userLink.linkTitle,
                link = userLink.link
            )
        }

        fun contactEntityToDTO(
            contact: ContactEntity,
        ): Contactdto {
            return Contactdto(
                contactType = contact.contactType.toString(),
                contactValue = contact.contactValue,
                isConfirmed = contact.isConfirmed
            )
        }
    }
}
