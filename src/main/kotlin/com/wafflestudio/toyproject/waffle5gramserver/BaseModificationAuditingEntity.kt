package com.wafflestudio.toyproject.waffle5gramserver

import jakarta.persistence.EntityListeners
import jakarta.persistence.MappedSuperclass
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime

@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
abstract class BaseModificationAuditingEntity : BaseAuditingEntity() {
    @LastModifiedDate
    protected var modifiedAt: LocalDateTime = LocalDateTime.MIN
}
