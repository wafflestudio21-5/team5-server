package com.wafflestudio.toyproject.waffle5gramserver.reply.repository

import com.wafflestudio.toyproject.waffle5gramserver.comment.repository.CommentEntity
import com.wafflestudio.toyproject.waffle5gramserver.user.repository.UserEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDateTime

@Entity(name = "replies")
class ReplyEntity (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,
    var content: String,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id")
    val comment: CommentEntity,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    val author: UserEntity,

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    var createdAt: LocalDateTime = LocalDateTime.now(),

    @UpdateTimestamp
    @Column(name = "modified_at", nullable = false)
    var modifiedAt: LocalDateTime = LocalDateTime.now()
) {
    fun updateContent(content: String) {
        this.content = content
    }
}