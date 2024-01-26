package com.wafflestudio.toyproject.waffle5gramserver.user.repository

import com.wafflestudio.toyproject.waffle5gramserver.BaseModificationAuditingEntity
import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.OneToMany
import java.util.Date

@Entity(name = "users")
class UserEntity(
    @Column(unique = true)
    var username: String,
    var name: String,
    var password: String? = null,
    var birthday: Date? = null,
    var isPrivate: Boolean = false,
    var gender: String? = null,
    var isCustomGender: Boolean = false,
    @Column(columnDefinition = "TEXT")
    var profileImageUrl: String? = null,
    @Column(columnDefinition = "TEXT")
    var bio: String? = null,
    @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL], orphanRemoval = true)
    var contacts: MutableList<ContactEntity> = mutableListOf(),
    @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL], orphanRemoval = true)
    val facebookUsers: MutableList<FacebookUserEntity> = mutableListOf(),
    @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL], orphanRemoval = true)
    val userLinks: MutableList<UserLinkEntity> = mutableListOf(),
) : BaseModificationAuditingEntity()
