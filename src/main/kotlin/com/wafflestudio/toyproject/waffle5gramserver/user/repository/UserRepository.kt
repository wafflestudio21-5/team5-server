package com.wafflestudio.toyproject.waffle5gramserver.user.repository

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import java.util.Optional

interface UserRepository : JpaRepository<UserEntity, Long> {

    fun findByUsername(username: String): Optional<UserEntity>

    override fun findById(id: Long): Optional<UserEntity>

    @Modifying
    @Query(value = "update users u set u.name = :name where u.id = :id")
    fun updateNameById(id: Long, name: String)

    @Modifying
    @Query(value = "update users u set u.username = :username where u.id = :id")
    fun updateUsernameById(id: Long, username: String)

    @Modifying
    @Query(value = "update users u set u.bio = :bio where u.id = :id")
    fun updateBioById(id: Long, bio: String?)

    @Modifying
    @Query(value = "update users u set u.gender = :gender, u.isCustomGender= :isCustomGender where u.id = :id")
    fun updateGenderAndIsCustomGenderById(id: Long, gender: String?, isCustomGender: Boolean)

    @Modifying(clearAutomatically = true)
    @Query(value = "update users u set u.isPrivate = :isPrivate where u.id = :Id")
    fun updateIsPrivateById(isPrivate: Boolean, Id: Long)

    fun existsByUsername(username: String): Boolean

    @Query(
        """
        SELECT u FROM users u
        JOIN FETCH u.facebookUsers f
        WHERE f.facebookId = :facebookId
    """
    )
    fun findByFacebookId(facebookId: Long): UserEntity?

    @Modifying
    @Query(value = "update users u set u.profileImageUrl = :profileImageUrl where u.id = :id")
    fun updateProfileImageUrlById(id: Long, profileImageUrl: String)

    @Modifying
    @Query(
        """
        SELECT u FROM users u
        WHERE u.username LIKE %:text%
        OR u.name LIKE %:text%"""
    )
    fun findAllByText(text: String): MutableList<UserEntity>

    fun findUserEntitiesByUsernameContainingOrNameContaining(text1: String, text2: String, pageable: Pageable): Page<UserEntity>
}
