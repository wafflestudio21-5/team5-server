package com.wafflestudio.toyproject.waffle5gramserver.search.repository

import com.wafflestudio.toyproject.waffle5gramserver.BaseAuditingEntity
import com.wafflestudio.toyproject.waffle5gramserver.user.repository.UserEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne

@Entity(name = "recent_searchs")
class RecentSearchEntity(
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    val user: UserEntity,
    @Column(name = "isText", nullable = false)
    val isText: Boolean,
    @Column(name = "text", nullable = true)
    val text: String?,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "search_user_id", nullable = true)
    val searchUser: UserEntity?,
) : BaseAuditingEntity()
