package com.wafflestudio.toyproject.waffle5gramserver.post.repository

import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Slice
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface PostRepository : JpaRepository<PostEntity, Long> {
    fun findPostsByUserId(userId: Long): List<PostEntity>

    @Query("SELECT p FROM posts p WHERE p.user.id = :userId")
    fun findAllByUserId(userId: Long): List<PostEntity>

    @Query(
        """
        SELECT p FROM posts p 
        WHERE p.user.isPrivate = false 
        AND p.user.id <> :currentUserId
        """
    )
    fun findSlice(pageable: Pageable, currentUserId: Long): Slice<PostEntity>

    @Query(
        """
        SELECT p FROM posts p 
        WHERE p.user.isPrivate = false 
        AND p.user.id <> :currentUserId
        AND p.category = :category
        """
    )
    fun findSliceByCategory(
        pageable: Pageable,
        category: PostCategory,
        currentUserId: Long,
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
    fun findSliceCommentDisplayedOrderByCommentCountDescByCategory(pageable: Pageable, category: PostCategory): Slice<PostEntityWithCommentCount>

    @Query(
        """
            SELECT p AS postEntity, 
            p.user.id AS userId, 
            p.user.username AS username, 
            p.user.profileImageUrl AS profileImageUrl,
            (SELECT COUNT(pl) FROM post_likes pl WHERE pl.userId = :currentUserId AND pl.postId = p.id) AS postLikeCount,
            (SELECT COUNT(ps) FROM post_saves ps WHERE ps.userId = :currentUserId AND ps.postId = p.id) AS postSaveCount,
            COUNT(c.id) AS commentCount
            FROM posts p
            INNER JOIN comments c
            WHERE p.user.isPrivate = false
            AND p.user.id <> :currentUserId
            GROUP BY p
        """
    )
    fun findSlicedPostDetails(pageable: Pageable, currentUserId: Long): Slice<PostDetailQueryResult>

    fun findByIdIn(postIds: List<Long>): List<PostEntity>

    @Query(
        """
            SELECT p AS postEntity, 
            p.user.id AS userId, 
            p.user.username AS username, 
            p.user.profileImageUrl AS profileImageUrl,
            (SELECT COUNT(pl) FROM post_likes pl WHERE pl.userId = :currentUserId AND pl.postId = p.id) AS postLikeCount,
            (SELECT COUNT(ps) FROM post_saves ps WHERE ps.userId = :currentUserId AND ps.postId = p.id) AS postSaveCount,
            COUNT(c.id) AS commentCount
            FROM posts p
            INNER JOIN comments c
            WHERE p.user.isPrivate = false
            AND p.user.id <> :currentUserId
            AND p.id = :postId
            GROUP BY p
        """
    )
    fun findSlicedPostDetailsByPostId(
        pageable: Pageable,
        currentUserId: Long,
        postId: Long
    ): Slice<PostDetailQueryResult>
}
