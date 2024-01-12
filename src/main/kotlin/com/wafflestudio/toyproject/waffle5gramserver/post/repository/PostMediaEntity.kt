package com.wafflestudio.toyproject.waffle5gramserver.post.repository

import jakarta.persistence.*

@Entity(name = "post_medias")
class PostMediaEntity (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0L,

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
    )