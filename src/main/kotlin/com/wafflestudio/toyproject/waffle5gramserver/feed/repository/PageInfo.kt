package com.wafflestudio.toyproject.waffle5gramserver.feed.repository

data class PageInfo (
    val currentPage: Int,
    val pageSize: Int,
    val totalItems: Long,
    val totalPages: Int
)