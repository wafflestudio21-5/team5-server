package com.wafflestudio.toyproject.waffle5gramserver.post.repository

import org.springframework.data.jpa.repository.JpaRepository

interface PostRepository : JpaRepository<PostEntity, Long>
