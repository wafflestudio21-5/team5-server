package com.wafflestudio.toyproject.waffle5gramserver.post.repository

import com.wafflestudio.toyproject.waffle5gramserver.BaseAuditingEntity
import jakarta.persistence.*

@Entity(name = "post_medias")
class PostMediaEntity (

    @Column(name = "media_url", nullable = false)
    var mediaUrl: String = "",

    @Column(name = "media_type", nullable = false)
    @Enumerated(EnumType.STRING)
    var mediaType: MediaType = MediaType.IMAGE,

    @Column(name = "media_order", nullable = false)
    var mediaOrder: Int = 0,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    var post: PostEntity,
) : BaseAuditingEntity()