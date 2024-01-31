package com.wafflestudio.toyproject.waffle5gramserver.search.dto

import com.wafflestudio.toyproject.waffle5gramserver.user.dto.MiniProfile

data class RecentSearch(
    val searchId : Long,
    val isText : Boolean,
    val text : String?,
    val miniProfile: MiniProfile?,
)
