package com.wafflestudio.toyproject.waffle5gramserver.post.repository

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

interface PostRepository : JpaRepository<PostEntity, Long> {
    fun findAllByUserIdIsNotOrderByCreatedAtDesc(userId: Long, pageable: Pageable): Page<PostEntity>
}
