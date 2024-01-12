package com.wafflestudio.toyproject.waffle5gramserver.post.repository

import com.wafflestudio.toyproject.waffle5gramserver.comment.repository.CommentEntity
import jakarta.persistence.*
import org.hibernate.annotations.UpdateTimestamp
import com.wafflestudio.toyproject.waffle5gramserver.user.repository.UserEntity
import org.springframework.data.annotation.CreatedDate
import java.time.LocalDateTime

@Entity(name = "posts")
class PostEntity (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0L,

    @Column(name = "content", nullable = false)
    var content: String = "",

    @Column(name = "like_count_displayed", nullable = false)
    var likeCountDisplayed: Boolean = true,

    @Column(name = "comment_displayed", nullable = false)
    var commentDisplayed: Boolean = true,

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    var createdAt: LocalDateTime = LocalDateTime.now(),

    @UpdateTimestamp
    @Column(name = "modified_at", nullable = false)
    var modifiedAt: LocalDateTime = LocalDateTime.now(),

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    var user: UserEntity,

    @OneToMany(mappedBy = "post", cascade = [CascadeType.ALL], orphanRemoval = true)
    var medias: MutableList<PostMediaEntity> = mutableListOf(),

    @OneToMany(mappedBy = "post", cascade = [CascadeType.ALL], orphanRemoval = true)
    var comments: MutableList<CommentEntity> = mutableListOf(),
    ) {
        fun addMedia(media: PostMediaEntity) {
            medias.add(media)
        }
    }
