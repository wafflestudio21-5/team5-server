package com.wafflestudio.toyproject.waffle5gramserver.search.repository

import com.wafflestudio.toyproject.waffle5gramserver.user.repository.UserEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query

interface RecentSearchRepository : JpaRepository<RecentSearchEntity, Long> {

    @Modifying
    @Query(
        """
        SELECT s FROM recent_searchs s
        WHERE s.user.id = :userId
        ORDER BY s.createdAt DESC
    """
    )
    fun searchAllByUserIdOrderByCreatedAt(userId: Long,): MutableList<RecentSearchEntity>?

    fun deleteAllByUser(user: UserEntity)
}
