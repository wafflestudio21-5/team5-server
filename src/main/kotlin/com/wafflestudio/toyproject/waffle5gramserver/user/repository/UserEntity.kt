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
    val username: String,
    var name: String,
    val password: String,
    val birthday: Date,
    val isPrivate: Boolean,
    val gender: String?,
    val isCustomGender: Boolean,
    @Column(columnDefinition = "TEXT")
    val profileImageUrl: String?,
    @Column(columnDefinition = "TEXT")
    val bio: String?,
    @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL], orphanRemoval = true)
    val contacts: MutableList<ContactEntity> = mutableListOf(),
    @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL], orphanRemoval = true)
    val userLinks: MutableList<UserLinkEntity> = mutableListOf(),
) : BaseModificationAuditingEntity()
