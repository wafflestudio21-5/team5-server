package com.wafflestudio.toyproject.waffle5gramserver.user.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface UserLinkRepository : JpaRepository<UserLinkEntity, Long> {
    @Query("SELECT l FROM user_links l WHERE l.user.id = :userId")
    fun findAllByUserId(userId: Long): MutableList<UserLinkEntity>
}
