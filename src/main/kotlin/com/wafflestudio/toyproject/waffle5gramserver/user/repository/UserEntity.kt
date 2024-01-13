package com.wafflestudio.toyproject.waffle5gramserver.user.repository

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import java.time.LocalDateTime
import java.util.Date

@Entity(name = "users")
class UserEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    val username: String,
    val name: String,
    val password: String,
    val birthday: Date,
    val isPrivate: Boolean,
    val createdAt: LocalDateTime,
    val modifiedAt: LocalDateTime,
    val pronoun: String?,
    @Column(columnDefinition = "TEXT")
    val profileImage: String?,
    @Column(columnDefinition = "TEXT")
    val bio: String?
)