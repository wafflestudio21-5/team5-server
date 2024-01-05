package com.wafflestudio.toyproject.waffle5gramserver.comment.repository

import org.springframework.data.jpa.repository.JpaRepository

interface CommentRepository : JpaRepository<CommentEntity, Long>{
}