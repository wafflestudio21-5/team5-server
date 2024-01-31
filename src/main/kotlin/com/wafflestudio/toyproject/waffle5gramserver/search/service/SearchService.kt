package com.wafflestudio.toyproject.waffle5gramserver.search.service

import com.wafflestudio.toyproject.waffle5gramserver.search.dto.MiniProfilePageResponse
import com.wafflestudio.toyproject.waffle5gramserver.search.dto.RecentSearch
import com.wafflestudio.toyproject.waffle5gramserver.user.dto.MiniProfile
import com.wafflestudio.toyproject.waffle5gramserver.user.service.InstagramUser

interface SearchService {

    fun getUserSearchPreviewList(authUser: InstagramUser, text: String,): MutableList<MiniProfile>?

    fun getUserSearchAllList(authUser: InstagramUser, text: String, page: Int, size: Int,): MiniProfilePageResponse

    fun applyTextToRecentSearch(authUser: InstagramUser, text: String)

    fun applyUserToRecentSearch(authUser: InstagramUser, username: String)

    fun getRecentSearchList(authUser: InstagramUser) : MutableList<RecentSearch>?

    fun removeRecentSearch(authUser: InstagramUser, searchId: Long,) : MutableList<RecentSearch>?

    fun removeAllRecentSearch(authUser: InstagramUser,)
}