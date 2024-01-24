package com.wafflestudio.toyproject.waffle5gramserver.post.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface PostMediaRepository : JpaRepository<PostMediaEntity, Long> {
    fun findByPostIdOrderByMediaOrder(postId: Long): List<PostMediaEntity>

    @Query(
        """
        select pm.mediaUrl
        from post_medias pm
        where pm.post.id = :postId
    """
    )
    fun findAllByPostId(postId: Long): List<String>
}
