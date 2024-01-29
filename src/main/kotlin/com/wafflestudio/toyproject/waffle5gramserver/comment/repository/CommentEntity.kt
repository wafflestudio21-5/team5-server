package com.wafflestudio.toyproject.waffle5gramserver.comment.repository

import com.wafflestudio.toyproject.waffle5gramserver.post.repository.PostEntity
import com.wafflestudio.toyproject.waffle5gramserver.user.repository.UserEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import java.time.LocalDateTime

@Entity(name = "comments")
class CommentEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0L,
    @Column(nullable = false)
    var text: String = "",
    @Column(nullable = false)
    var likeCount: Int = 0,
    @Column(nullable = false)
    val createdAt: LocalDateTime = LocalDateTime.now(),
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    val post: PostEntity,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    val user: UserEntity,
) {
    fun incrementLikeCount() {
        likeCount += 1
    }

    fun decrementLikeCount() {
        likeCount -= 1
    }
}
