package com.wafflestudio.toyproject.waffle5gramserver.search.mapper

import com.wafflestudio.toyproject.waffle5gramserver.search.dto.RecentSearch
import com.wafflestudio.toyproject.waffle5gramserver.search.repository.RecentSearchEntity
import com.wafflestudio.toyproject.waffle5gramserver.user.dto.MiniProfile

class ToRecentSearch {
    companion object {
        fun toRecentSearchdto(
            recentSearchEntity: RecentSearchEntity,
        ): RecentSearch {
            val searchUser = recentSearchEntity.searchUser
            if (searchUser != null) {
                return RecentSearch(
                    searchId = recentSearchEntity.id,
                    isText = recentSearchEntity.isText,
                    text = recentSearchEntity.text,
                    miniProfile = MiniProfile(
                        userId = recentSearchEntity.searchUser.id,
                        username = recentSearchEntity.searchUser.username,
                        name = recentSearchEntity.searchUser.name,
                        profileImageUrl = recentSearchEntity.searchUser.profileImageUrl,
                    )
                )
            } else {
                return RecentSearch(
                    searchId = recentSearchEntity.id,
                    isText = recentSearchEntity.isText,
                    text = recentSearchEntity.text,
                    miniProfile = null
                )
            }
        }
    }
}
