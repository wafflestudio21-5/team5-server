package com.wafflestudio.toyproject.waffle5gramserver.user.repository

import com.wafflestudio.toyproject.waffle5gramserver.BaseAuditingEntity
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne

@Entity(name = "user_links")
class UserLinkEntity(
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", nullable = false)
    val user: UserEntity,
    val link: String,
    val linkTitle: String,
) : BaseAuditingEntity()
