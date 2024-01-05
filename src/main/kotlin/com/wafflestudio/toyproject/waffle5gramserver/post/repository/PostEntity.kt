package com.wafflestudio.toyproject.waffle5gramserver.post.repository

import com.wafflestudio.toyproject.waffle5gramserver.BaseModificationAuditingEntity
import jakarta.persistence.Entity

@Entity(name = "posts")
class PostEntity(
    var caption: String,
): BaseModificationAuditingEntity() {
}