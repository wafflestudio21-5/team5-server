package com.wafflestudio.toyproject.waffle5gramserver.reply.repository

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity
class ReplyEntity (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = 0L,
    val content: String,
) : BaseTimeEntity() {
}