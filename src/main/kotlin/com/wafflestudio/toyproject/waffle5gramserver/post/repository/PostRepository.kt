package com.wafflestudio.toyproject.waffle5gramserver.post.repository

import com.wafflestudio.toyproject.waffle5gramserver.post.service.PostEntityWithCommentCount
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Slice
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface PostRepository : JpaRepository<PostEntity, Long> {
    // 사용자 ID와 커서 기반으로 게시물 목록 조회
    @Query("SELECT p FROM posts p WHERE p.user.id = :userId AND p.id < :cursor ORDER BY p.id DESC")
    fun findPostsByUserIdAndCursor(
        userId: Long,
        cursor: Long,
        pageable: Pageable,
    ): List<PostEntity>

    fun findPostsByUserId(
        userId: Long,
        pageable: Pageable,
    ): Slice<PostEntity>

    fun findPostsByUserId(userId: Long): List<PostEntity>

    // 사용자 ID로 최신 게시물 목록 조회 (초기 로드용)
    @Query("SELECT p FROM posts p WHERE p.user.id = :userId ORDER BY p.id DESC")
    fun findLatestPostsByUserId(
        userId: Long,
        pageable: Pageable,
    ): List<PostEntity>

    @Query("SELECT p FROM posts p WHERE p.user.id = :userId")
    fun findAllByUserId(userId: Long): List<PostEntity>

    @Query("SELECT p FROM posts p WHERE p.user.isPrivate = false")
    fun findSlice(pageable: Pageable): Slice<PostEntity>

    @Query("SELECT p FROM posts p WHERE p.user.isPrivate = false AND p.category = :category")
    fun findSliceByCategory(
        pageable: Pageable,
        category: PostCategory,
    ): Slice<PostEntity>

    @Query(
        """
            SELECT p FROM posts p
            WHERE p.user.isPrivate = false
            AND p.likeCountDisplayed = true
        """,
    )
    fun findSliceLikeCountDisplayed(pageable: Pageable): Slice<PostEntity>

    @Query(
        """
            SELECT p FROM posts p
            WHERE p.user.isPrivate = false
            AND p.likeCountDisplayed = true
            AND p.category = :category
        """,
    )
    fun findSliceLikeCountDisplayedByCategory(
        pageable: Pageable,
        category: PostCategory,
    ): Slice<PostEntity>

    @Query(
        """
            SELECT p AS postEntity, COUNT(c.id) AS commentCount FROM posts p
            INNER JOIN comments c
            WHERE p.user.isPrivate = false
            AND p.commentDisplayed = true
            GROUP BY p
            ORDER BY COUNT(c.id) DESC
        """,
    )
    fun findSliceCommentDisplayedOrderByCommentCountDesc(pageable: Pageable): Slice<PostEntityWithCommentCount>

    @Query(
        """
            SELECT p AS postEntity, COUNT(c.id) AS commentCount FROM posts p
            INNER JOIN comments c
            WHERE p.user.isPrivate = false
            AND p.commentDisplayed = true
            AND p.category = :category
            GROUP BY p
            ORDER BY COUNT(c.id) DESC
        """,
    )
    fun findSliceCommentDisplayedOrderByCommentCountDescByCategory(
        pageable: Pageable,
        category: PostCategory,
    ): Slice<PostEntityWithCommentCount>

    fun findByIdIn(
        postIds: List<Long>,
        pageable: Pageable,
    ): Slice<PostEntity>

    fun findByIdIn(postIds: List<Long>): List<PostEntity>
}
