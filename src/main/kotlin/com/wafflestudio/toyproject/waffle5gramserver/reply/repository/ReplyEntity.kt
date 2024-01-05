package com.wafflestudio.toyproject.waffle5gramserver.reply.repository

import com.wafflestudio.toyproject.waffle5gramserver.BaseModificationAuditingEntity
import com.wafflestudio.toyproject.waffle5gramserver.comment.repository.CommentEntity
// import com.wafflestudio.toyproject.waffle5gramserver.comment.repository.CommentEntity
import com.wafflestudio.toyproject.waffle5gramserver.user.repository.UserEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne

@Entity(name = "replies")
class ReplyEntity (
    @Column(columnDefinition = "LONGTEXT")
    var content: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    val user: UserEntity,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id", nullable = false)
    val comment: CommentEntity,
) : BaseModificationAuditingEntity() {
    fun updateContent(content: String) {
        this.content = content
    }

    fun replyId(): Long {
        return this.id
    }
}