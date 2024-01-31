package com.wafflestudio.toyproject.waffle5gramserver.search.service

import com.wafflestudio.toyproject.waffle5gramserver.search.repository.RecentSearchRepository
import com.wafflestudio.toyproject.waffle5gramserver.user.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class SearchServiceImpl (
    private val userRepository: UserRepository,
    private val recentSearchRepository: RecentSearchRepository,
) : SearchService {

}