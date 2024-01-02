package com.wafflestudio.toyproject.waffle5gramserver.reply.repository

import org.springframework.data.jpa.repository.JpaRepository

interface ReplyRepository : JpaRepository<ReplyEntity, Long> {
}