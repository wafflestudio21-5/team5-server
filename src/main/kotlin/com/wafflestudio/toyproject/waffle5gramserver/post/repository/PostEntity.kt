package com.wafflestudio.toyproject.waffle5gramserver.post.repository

import com.wafflestudio.toyproject.waffle5gramserver.BaseModificationAuditingEntity
import com.wafflestudio.toyproject.waffle5gramserver.comment.repository.CommentEntity
import com.wafflestudio.toyproject.waffle5gramserver.user.repository.UserEntity
import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany

@Entity(name = "posts")
class PostEntity(
    @Column(name = "content", nullable = false)
    var content: String = "",
    @Column(name = "like_count", nullable = false)
    var likeCount: Int = 0,
    @Column(name = "like_count_displayed", nullable = false)
    var likeCountDisplayed: Boolean = true,
    @Column(name = "comment_displayed", nullable = false)
    var commentDisplayed: Boolean = true,
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    var category: PostCategory,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    var user: UserEntity,
    @OneToMany(mappedBy = "post", cascade = [CascadeType.ALL], orphanRemoval = true)
    var medias: MutableList<PostMediaEntity> = mutableListOf(),
    @OneToMany(mappedBy = "post", cascade = [CascadeType.ALL], orphanRemoval = true)
    var comments: MutableList<CommentEntity> = mutableListOf(),
) : BaseModificationAuditingEntity() {
    fun addMedia(media: PostMediaEntity) {
        medias.add(media)
    }

    fun incrementLikeCount() {
        likeCount += 1
    }

    fun decrementLikeCount() {
        likeCount -= 1
    }
}
