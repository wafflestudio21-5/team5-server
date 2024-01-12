package com.wafflestudio.toyproject.waffle5gramserver.post.repository

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface PostRepository : JpaRepository<PostEntity, Long> {
    fun findByUserIdOrderByCreatedAtDesc(userId: Long, pageable: Pageable): Page<PostEntity>

//    @Query("""
//        SELECT P FROM posts P
//        WHERE P.id IN (
//            SELECT F.following_id FROM follows F
//            WHERE F.user_id = :userId
//        ) AND P.id NOT IN (
//            SELECT L.post_id FROM post_likes L
//            WHERE L.user_id = :userId
//        )
//    """)
//    fun findAllByFollowingAndUnliked(userId: Long, pageable: Pageable): Page<PostEntity>
}