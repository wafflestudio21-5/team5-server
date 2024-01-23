package com.wafflestudio.toyproject.waffle5gramserver.follow.repository

import com.wafflestudio.toyproject.waffle5gramserver.BaseModificationAuditingEntity
import com.wafflestudio.toyproject.waffle5gramserver.user.repository.UserEntity
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne

@Entity(name = "follows")
class FollowEntity (
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "followerUserId")
    val follower: UserEntity,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "followeeUserId")
    val followee: UserEntity,
) : BaseModificationAuditingEntity()