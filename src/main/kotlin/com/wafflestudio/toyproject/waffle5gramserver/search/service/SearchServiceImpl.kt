package com.wafflestudio.toyproject.waffle5gramserver.search.service

import com.wafflestudio.toyproject.waffle5gramserver.feed.controller.PageInfo
import com.wafflestudio.toyproject.waffle5gramserver.follow.repository.FollowRepository
import com.wafflestudio.toyproject.waffle5gramserver.global.error_handling.ErrorCode
import com.wafflestudio.toyproject.waffle5gramserver.global.error_handling.global_exception.EntityNotFoundException
import com.wafflestudio.toyproject.waffle5gramserver.profile.mapper.ProfileResponseMapper
import com.wafflestudio.toyproject.waffle5gramserver.search.dto.MiniProfilePageResponse
import com.wafflestudio.toyproject.waffle5gramserver.search.dto.RecentSearch
import com.wafflestudio.toyproject.waffle5gramserver.search.mapper.ToRecentSearch
import com.wafflestudio.toyproject.waffle5gramserver.search.repository.RecentSearchEntity
import com.wafflestudio.toyproject.waffle5gramserver.search.repository.RecentSearchRepository
import com.wafflestudio.toyproject.waffle5gramserver.user.dto.MiniProfile
import com.wafflestudio.toyproject.waffle5gramserver.user.repository.UserRepository
import com.wafflestudio.toyproject.waffle5gramserver.user.service.InstagramUser
import jakarta.transaction.Transactional
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
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
        val ResultUsers = userRepository.findAllByText(text)
        val userfollows = followRepository.findAllByFollowerUserId(authUser.id).map { it.followee }.toMutableList()
        val userfollowings = followRepository.findAllByFolloweeUserId(authUser.id).map { it.follower }.toMutableList()

        val followSearchResult = userfollows.intersect(ResultUsers)
        val followingSearchResult = userfollowings.intersect(ResultUsers)

        if (followSearchResult.count() >= 5) {
            return followSearchResult.take(5).map { ProfileResponseMapper.toMiniProfile(it) }.toMutableList()
        } else if (followSearchResult.count() + followingSearchResult.count() >= 5) {
            val tempFollowingSearchResult = followingSearchResult.take(5 - followSearchResult.count())
            val finalSearchResult = followSearchResult.union(tempFollowingSearchResult)
            return finalSearchResult.map { ProfileResponseMapper.toMiniProfile(it) }.toMutableList()
        } else {
            val finalSearchResult = followSearchResult.union(followingSearchResult)
            return finalSearchResult.map { ProfileResponseMapper.toMiniProfile(it) }.toMutableList()
        }
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
        val pageable: Pageable = PageRequest.of(page, size)
        val pageUserList = userRepository.findAllByUsernameOrNameContaining(text, pageable)
        return MiniProfilePageResponse(
            miniProfiles = pageUserList.content.map { ProfileResponseMapper.toMiniProfile(it) }.toMutableList(),
            pageInfo = PageInfo(
                page = pageUserList.number + 1,
                size = pageUserList.size,
                offset = pageUserList.pageable.offset,
                hasNext = pageUserList.hasNext(),
                elements = pageUserList.numberOfElements,
            )
        )
    }

    @Transactional
    override fun applyTextToRecentSearch(
        authUser: InstagramUser,
        text: String
    ) {
        // 텍스트 검색 기록을 recentsearch 엔티티에 추가
        // 응답할건 없음
        val authUserEntity = userRepository.findById(authUser.id)
            .orElseThrow { EntityNotFoundException(ErrorCode.USER_NOT_FOUND) }
        recentSearchRepository.save(
            RecentSearchEntity(
                user = authUserEntity,
                isText = true,
                text = text,
                searchUser = null
            )
        )
    }

    @Transactional
    override fun applyUserToRecentSearch(
        authUser: InstagramUser,
        username: String
    ) {
        // 유저 검색 기록을 recentsearch 엔티티에 추가
        // 응답할 건 없음
        val authUserEntity = userRepository.findById(authUser.id)
            .orElseThrow { EntityNotFoundException(ErrorCode.USER_NOT_FOUND) }
        val searchUserEntity = userRepository.findByUsername(username)
            .orElseThrow { EntityNotFoundException(ErrorCode.USER_NOT_FOUND) }
        recentSearchRepository.save(
            RecentSearchEntity(
                user = authUserEntity,
                isText = false,
                text = null,
                searchUser = searchUserEntity
            )
        )
    }

    @Transactional
    override fun getRecentSearchList(
        authUser: InstagramUser
    ): MutableList<RecentSearch>? {
        // 현재 로그인한 유저의 최근 검색 기록 20개를 리스트 조회
        // 응답시 RecentSearch형으로 내려줘야함.
        val recentSearchs = recentSearchRepository.searchAllByUserIdOrderByCreatedAt(authUser.id)
        if (recentSearchs == null) return mutableListOf()
        else {
            return recentSearchs.take(20).map { ToRecentSearch.toRecentSearchdto(it) }.toMutableList()
        }
    }

    @Transactional
    override fun removeRecentSearch(
        authUser: InstagramUser,
        searchId: Long
    ): MutableList<RecentSearch>? {
        // searchId에 맞는 검색기록을 삭제
        // 삭제 후에 최근 검색 기록 20개를 RecentSearch의 리스트 형으로 응답
        val recentSearch = recentSearchRepository.findById(searchId)
            .orElseThrow { EntityNotFoundException(ErrorCode.RECENT_SEARCH_NOT_FOUND) }
        recentSearchRepository.delete(recentSearch)
        val recentSearchs = recentSearchRepository.searchAllByUserIdOrderByCreatedAt(authUser.id)
        if (recentSearchs == null) return mutableListOf()
        else {
            return recentSearchs.take(20).map { ToRecentSearch.toRecentSearchdto(it) }.toMutableList()
        }
    }

    @Transactional
    override fun removeAllRecentSearch(
        authUser: InstagramUser
    ) {
        recentSearchRepository.deleteAllByUserId(authUser.id)
    }
}
