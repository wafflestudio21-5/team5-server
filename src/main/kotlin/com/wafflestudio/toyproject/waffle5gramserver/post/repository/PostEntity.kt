package com.wafflestudio.toyproject.waffle5gramserver.post.repository

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import com.wafflestudio.toyproject.waffle5gramserver.user.repository.UserEntity
import java.time.LocalDateTime

@Entity(name = "posts")
class PostEntity (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0L,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    var user: UserEntity,

    @Lob
    var caption: String = "",

    @Column(name = "like_count_displayed", nullable = false)
    var likeCountDisplayed: Boolean = false,

    @Column(name = "comment_displayed", nullable = false)
    var commentDisplayed: Boolean = false,

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    var createdAt: LocalDateTime = LocalDateTime.now(),

    @UpdateTimestamp
    @Column(name = "modified_at", nullable = false)
    var modifiedAt: LocalDateTime = LocalDateTime.now(),
)