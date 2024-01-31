package com.wafflestudio.toyproject.waffle5gramserver.search.service

import com.wafflestudio.toyproject.waffle5gramserver.follow.repository.FollowRepository
import com.wafflestudio.toyproject.waffle5gramserver.search.dto.MiniProfilePageResponse
import com.wafflestudio.toyproject.waffle5gramserver.search.dto.RecentSearch
import com.wafflestudio.toyproject.waffle5gramserver.search.repository.RecentSearchRepository
import com.wafflestudio.toyproject.waffle5gramserver.user.dto.MiniProfile
import com.wafflestudio.toyproject.waffle5gramserver.user.repository.UserRepository
import com.wafflestudio.toyproject.waffle5gramserver.user.service.InstagramUser
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class SearchServiceImpl(
    private val userRepository: UserRepository,
    private val recentSearchRepository: RecentSearchRepository,
    private val followRepository: FollowRepository
) : SearchService {

    @Transactional
    override fun getUserSearchPreviewList(
        authUser: InstagramUser,
        text: String
    ): MutableList<MiniProfile>? {
        // text랑 유저네임 혹은 네임이 비슷한 유저들의 목록을 반환
        // 자신이 팔로우하는 유저 먼저 뽑고, 자신을 팔로우하는 유저 나중에 뽑아서 합치기
        // 최대 5개까지 미니프로필 응답
        TODO()
    }

    @Transactional
    override fun getUserSearchAllList(
        authUser: InstagramUser,
        text: String,
        page: Int,
        size: Int
    ): MiniProfilePageResponse {
        // text랑 유저네임 혹은 네임이 비슷한 유저들의 목록을 반환
        // 위에처럼 팔로워 팔로우 기준은 없고, 그냥 무작위로 비슷한거 뽑기
        // 20개씩 미니프로필 응답하고, 페이지네이션 조회
        TODO("Not yet implemented")
    }

    @Transactional
    override fun applyTextToRecentSearch(
        authUser: InstagramUser,
        text: String
    ) {
        // 텍스트 검색 기록을 recentsearch 엔티티에 추가
        // 응답할건 없음
        TODO("Not yet implemented")
    }

    @Transactional
    override fun applyUserToRecentSearch(
        authUser: InstagramUser,
        username: String
    ) {
        // 유저 검색 기록을 recentsearch 엔티티에 추가
        // 응답할 건 없음
        TODO("Not yet implemented")
    }

    @Transactional
    override fun getRecentSearchList(
        authUser: InstagramUser
    ): MutableList<RecentSearch>? {
        // 현재 로그인한 유저의 최근 검색 기록 20개를 리스트 조회
        // 응답시 RecentSearch형으로 내려줘야함.
        TODO("Not yet implemented")
    }

    @Transactional
    override fun removeRecentSearch(
        authUser: InstagramUser,
        searchId: Long
    ): MutableList<RecentSearch>? {
        // searchId에 맞는 검색기록을 삭제
        // 삭제 후에 최근 검색 기록 20개를 RecentSearch의 리스트 형으로 응답
        TODO("Not yet implemented")
    }

    @Transactional
    override fun removeAllRecentSearch(
        authUser: InstagramUser
    ) {
        // 이 유저가 만든 모든 검색기록 다 날려버리기
        // 응답할건 없음
        TODO("Not yet implemented")
    }
}
