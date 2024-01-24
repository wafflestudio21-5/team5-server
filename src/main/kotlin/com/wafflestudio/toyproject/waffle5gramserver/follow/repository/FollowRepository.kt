package com.wafflestudio.toyproject.waffle5gramserver.follow.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface FollowRepository : JpaRepository<FollowEntity, Long> {
    @Query("SELECT f FROM follows AS f WHERE f.follower.id = :followerUserId AND f.followee.id = :followeeUserId")
    fun findByFollowerUserIdAndFolloweeUserId(followerUserId: Long, followeeUserId: Long): FollowEntity?
}
