package com.wafflestudio.toyproject.waffle5gramserver.feed.repository

import com.wafflestudio.toyproject.waffle5gramserver.post.repository.PostEntity
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Slice
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface FeedQueryRepository : JpaRepository<PostEntity, Long> {
    @Query(
        """
        select p from posts p
        where p.id not in (
            select pl.postId from post_likes pl where pl.userId = :userId
        ) and p.user.id != :userId
        order by p.createdAt desc
    """
    )
    fun findPostsNotCreatedByAndNotLikedByUser(userId: Long, pageable: Pageable): Slice<PostEntity>
}
