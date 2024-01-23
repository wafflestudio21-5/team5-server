package com.wafflestudio.toyproject.waffle5gramserver.follow.repository

import org.springframework.data.jpa.repository.JpaRepository

interface FollowRequestRepository:JpaRepository<FollowRequestEntity,Long> {
}