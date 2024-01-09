package com.wafflestudio.toyproject.waffle5gramserver.post.repository

import com.wafflestudio.toyproject.waffle5gramserver.BaseAuditingEntity
import com.wafflestudio.toyproject.waffle5gramserver.comment.repository.CommentEntity
import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import com.wafflestudio.toyproject.waffle5gramserver.user.repository.UserEntity
import java.time.LocalDateTime

@Entity(name = "posts")
class PostEntity (
    @Column(name = "content", nullable = false)
    var content: String = "",

    @Column(name = "like_count_displayed", nullable = false)
    var likeCountDisplayed: Boolean = true,

    @Column(name = "comment_displayed", nullable = false)
    var commentDisplayed: Boolean = true,

    @UpdateTimestamp
    @Column(name = "modified_at", nullable = false)
    var modifiedAt: LocalDateTime = LocalDateTime.now(),

    @OneToMany(mappedBy = "post", cascade = [CascadeType.ALL], orphanRemoval = true)
    var medias: MutableList<PostMediaEntity> = mutableListOf(),

    @OneToMany(mappedBy = "post", cascade = [CascadeType.ALL], orphanRemoval = true)
    var comments: MutableList<CommentEntity> = mutableListOf(),

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    var user: UserEntity,
    ) : BaseAuditingEntity()
