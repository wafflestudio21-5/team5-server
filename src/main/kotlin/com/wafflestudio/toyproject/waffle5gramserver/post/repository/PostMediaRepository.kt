package com.wafflestudio.toyproject.waffle5gramserver.post.repository

import org.springframework.data.jpa.repository.JpaRepository

interface PostMediaRepository: JpaRepository<PostMediaEntity, Long> {
    fun findByPostIdOrderByMediaOrder(postId: Long): List<PostMediaEntity>
}