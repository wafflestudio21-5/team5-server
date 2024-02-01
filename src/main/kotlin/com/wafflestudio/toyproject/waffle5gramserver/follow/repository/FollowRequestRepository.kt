package com.wafflestudio.toyproject.waffle5gramserver.follow.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface FollowRequestRepository : JpaRepository<FollowRequestEntity, Long> {
    @Query("SELECT f FROM follows_requests f WHERE f.follower.id = :followerUserId AND f.followee.id = :followeeUserId")
    fun findByFollowerUserIdAndFolloweeUserId(followerUserId: Long, followeeUserId: Long): FollowRequestEntity?

    @Query("SELECT f FROM follows_requests f WHERE f.followee.id = :followeeUserId")
    fun findAllByFolloweeUserId(followeeUserId: Long): List<FollowRequestEntity>

    @Query("SELECT f FROM follows_requests f WHERE f.follower.id = :followerUserId")
    fun findAllByFollowerUserId(followerUserId: Long): List<FollowRequestEntity>
}
