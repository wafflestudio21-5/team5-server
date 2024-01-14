package com.wafflestudio.toyproject.waffle5gramserver.user.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query

interface UserRepository : JpaRepository<UserEntity, Long> {
    @Modifying
    @Query(value = "update users u set u.name = :name where u.id = :id")
    fun updateNameById(id: Long, name: String)
}