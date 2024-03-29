package com.wafflestudio.toyproject.waffle5gramserver.post.repository

import org.springframework.data.jpa.repository.JpaRepository

interface PostSaveRepository : JpaRepository<PostSaveEntity, Long> {
    fun findByUserIdAndPostId(
        userId: Long,
        postId: Long,
    ): PostSaveEntity?

    fun deleteAllByPostId(postId: Long)

    fun findByPostIdAndUserId(
        postId: Long,
        userId: Long,
    ): PostSaveEntity?

    fun findByUserId(userId: Long): List<PostSaveEntity>
}
