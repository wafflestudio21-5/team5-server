package com.wafflestudio.toyproject.waffle5gramserver.user.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import java.util.Optional

interface UserRepository : JpaRepository<UserEntity, Long> {
    fun findByUsername(username: String): Optional<UserEntity>

    @Modifying
    @Query(value = "update users u set u.name = :name where u.id = :id")
    fun updateNameById(id: Long, name: String)

    @Modifying
    @Query(value = "update users u set u.isPrivate = :isPrivate where u.username = :username")
    fun updateIsPrivateByUsername(isPrivate: Boolean, username: String)

    fun existsByUsername(username: String): Boolean
}
