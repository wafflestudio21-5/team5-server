package com.wafflestudio.toyproject.waffle5gramserver.feed.controller

data class PageInfo(
    val page: Int,
    val size: Int,
    val offset: Long,
    val hasNext: Boolean,
    val elements: Int,
)
