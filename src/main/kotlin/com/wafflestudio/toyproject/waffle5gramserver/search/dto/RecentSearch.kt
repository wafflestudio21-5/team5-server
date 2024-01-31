package com.wafflestudio.toyproject.waffle5gramserver.search.dto

data class RecentSearch(
    val searchId : Long,
    val isText : Boolean,
    val text : String?,
    val userId: Long?,
    val username: String?,
    val name: String?,
    val profileImageUrl: String?,
)
