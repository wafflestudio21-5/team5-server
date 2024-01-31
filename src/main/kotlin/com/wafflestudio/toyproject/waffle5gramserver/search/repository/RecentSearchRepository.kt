package com.wafflestudio.toyproject.waffle5gramserver.search.repository

import org.springframework.data.jpa.repository.JpaRepository

interface RecentSearchRepository : JpaRepository<RecentSearchEntity, Long> {
}