package com.wafflestudio.toyproject.waffle5gramserver.post.repository

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Slice
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface PostRepository : JpaRepository<PostEntity, Long> {
    // 사용자 ID에 따른 포스트 목록 조회 (페이지네이션 지원)
    fun findByUserId(
        userId: Long,
        pageable: Pageable,
    ): Page<PostEntity>

    // 사용자 ID와 커서 기반으로 게시물 목록 조회
    @Query("SELECT p FROM posts p WHERE p.user.id = :userId AND p.id < :cursor ORDER BY p.id DESC")
    fun findPostsByUserIdAndCursor(
        userId: Long,
        cursor: Long,
        pageable: Pageable,
    ): List<PostEntity>

    // 사용자 ID로 최신 게시물 목록 조회 (초기 로드용)
    @Query("SELECT p FROM posts p WHERE p.user.id = :userId ORDER BY p.id DESC")
    fun findLatestPostsByUserId(
        userId: Long,
        pageable: Pageable,
    ): List<PostEntity>

    // 커서보다 ID가 큰 게시물 조회 (최신 게시물 로드)
    @Query("SELECT p FROM posts p WHERE p.user.id = :userId AND (:cursor IS NULL OR p.id > :cursor) ORDER BY p.id ASC")
    fun findNewerPostsByUserIdAndCursor(
        userId: Long,
        cursor: Long?,
        pageable: Pageable,
    ): List<PostEntity>

    // 커서보다 ID가 작은 게시물 조회 (이전 게시물 로드)
    @Query("SELECT p FROM posts p WHERE p.user.id = :userId AND (:cursor IS NULL OR p.id < :cursor) ORDER BY p.id DESC")
    fun findOlderPostsByUserIdAndCursor(
        userId: Long,
        cursor: Long?,
        pageable: Pageable,
    ): List<PostEntity>

    @Query("SELECT p FROM posts p WHERE p.user.id = :userId")
    fun findAllByUserId(
        userId: Long,
    ): List<PostEntity>

    @Query("SELECT p FROM posts p WHERE p.user.isPrivate = false")
    fun findSlice(pageable: Pageable): Slice<PostEntity>

    @Query("SELECT p FROM posts p WHERE p.user.isPrivate = false AND p.category = :category")
    fun findSliceByCategory(pageable: Pageable, category: PostCategory): Slice<PostEntity>

    @Query(
        """
            SELECT p FROM posts p
            WHERE p.user.isPrivate = false
            AND p.likeCountDisplayed = true
        """
    )
    fun findSliceLikeCountDisplayed(pageable: Pageable): Slice<PostEntity>

    @Query(
        """
            SELECT p FROM posts p
            WHERE p.user.isPrivate = false
            AND p.likeCountDisplayed = true
            AND p.category = :category
        """
    )
    fun findSliceLikeCountDisplayedByCategory(pageable: Pageable, category: PostCategory): Slice<PostEntity>

    @Query(
        """
            SELECT p FROM posts p
            WHERE p.user.isPrivate = false
            AND p.commentDisplayed = true
            ORDER BY COUNT(p.comments) DESC
        """
    )
    fun findSliceCommentDisplayedOrderByCommentCountDesc(pageable: Pageable): Slice<PostEntity>

    @Query(
        """
            SELECT p FROM posts p
            WHERE p.user.isPrivate = false
            AND p.commentDisplayed = true
            AND p.category = :category
            ORDER BY COUNT(p.comments) DESC
        """
    )
    fun findSliceCommentDisplayedOrderByCommentCountDescByCategory(pageable: Pageable, category: PostCategory): Slice<PostEntity>
}
