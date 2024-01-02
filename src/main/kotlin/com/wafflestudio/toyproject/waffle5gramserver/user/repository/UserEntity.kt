package com.wafflestudio.toyproject.waffle5gramserver.user.repository

import com.wafflestudio.toyproject.waffle5gramserver.BaseModificationAuditingEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import java.util.Date

@Entity(name = "users")
class UserEntity(
    @Column(unique = true)
    val username: String,
    var name: String,
    val password: String,
    val birthday: Date,
    val isPrivate: Boolean,
    val pronoun: String?,
    @Column(columnDefinition = "TEXT")
    val profileImageUrl: String?,
    @Column(columnDefinition = "TEXT")
    val bio: String?
) : BaseModificationAuditingEntity()
