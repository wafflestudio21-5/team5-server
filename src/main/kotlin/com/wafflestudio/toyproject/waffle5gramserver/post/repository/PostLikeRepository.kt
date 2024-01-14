package com.wafflestudio.toyproject.waffle5gramserver.post.repository

import org.springframework.data.jpa.repository.JpaRepository

interface PostLikeRepository : JpaRepository<PostLikeEntity, Long> {
    fun findByPostIdAndUserId(postId: Long, userId: Long): PostLikeEntity?
    fun countByPostId(postId: Long): Long
    fun countByPostIdAndUserId(postId: Long, userId: Long): Long
    fun deleteAllByPostId(postId: Long)
}
