package com.wafflestudio.toyproject.waffle5gramserver.user.repository

import com.wafflestudio.toyproject.waffle5gramserver.BaseAuditingEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne

@Entity(name = "facebook_users")
class FacebookUserEntity(
    @Column(unique = true)
    val facebookId: Long,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    val user: UserEntity
) : BaseAuditingEntity()
