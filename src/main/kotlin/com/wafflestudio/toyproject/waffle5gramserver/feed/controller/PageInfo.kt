package com.wafflestudio.toyproject.waffle5gramserver.feed.controller

data class PageInfo(
    val currentPage: Int,
    val pageSize: Int,
    val totalItems: Long,
    val totalPages: Int
)
