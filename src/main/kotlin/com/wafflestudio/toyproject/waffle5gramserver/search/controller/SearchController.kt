package com.wafflestudio.toyproject.waffle5gramserver.search.controller

import com.wafflestudio.toyproject.waffle5gramserver.search.dto.MiniProfilePageResponse
import com.wafflestudio.toyproject.waffle5gramserver.search.dto.RecentSearch
import com.wafflestudio.toyproject.waffle5gramserver.search.dto.TextRequestDto
import com.wafflestudio.toyproject.waffle5gramserver.search.dto.UsernameRequestDto
import com.wafflestudio.toyproject.waffle5gramserver.search.service.SearchService
import com.wafflestudio.toyproject.waffle5gramserver.user.dto.MiniProfile
import com.wafflestudio.toyproject.waffle5gramserver.user.service.InstagramUser
import jakarta.validation.Valid
import jakarta.validation.constraints.NotBlank
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/search")
class SearchController(
    private val searchService: SearchService,
) {

    // 유저 검색 미리보기 목록 조회
    @GetMapping("/preview")
    fun retrieveUserSearchPreviewList(
        @AuthenticationPrincipal authuser: InstagramUser,
        @RequestParam("text") @NotBlank text: String,
    ): ResponseEntity<MutableList<MiniProfile>> {
        val miniProfile = searchService.getUserSearchPreviewList(authuser, text)
        return ResponseEntity.ok(miniProfile)
    }

    // 유저 검색 모두보기 목록 조회
    @GetMapping("/all")
    fun retrieveUserSearchAllList(
        @AuthenticationPrincipal authuser: InstagramUser,
        @RequestParam("text") @NotBlank text: String,
        @RequestParam("page") page: Int = 1,
        @RequestParam("size") size: Int = 20,
    ): ResponseEntity<MiniProfilePageResponse> {
        val allList = searchService.getUserSearchAllList(authuser, text, page - 1, size)
        return ResponseEntity.ok(allList)
    }

    // 최근 검색 기록에 텍스트 추가
    @PostMapping("/recent/text")
    fun addTextToRecentSearch(
        @AuthenticationPrincipal authuser: InstagramUser,
        @RequestBody(required = true) @Valid textRequestDto: TextRequestDto,
    ): ResponseEntity<Any> {
        searchService.applyTextToRecentSearch(authuser, textRequestDto.text)
        return ResponseEntity.ok(null)
    }

    // 최근 검색 기록에 유저 추가
    @PostMapping("/recent/user")
    fun addUserToRecentSearch(
        @AuthenticationPrincipal authuser: InstagramUser,
        @RequestBody(required = true) @Valid usernameRequestDto: UsernameRequestDto,
    ): ResponseEntity<Any> {
        searchService.applyUserToRecentSearch(authuser, usernameRequestDto.username)
        return ResponseEntity.ok(null)
    }

    // 최근 검색 기록 목록 조회
    @GetMapping("/recent")
    fun retrieveRecentSearchList(
        @AuthenticationPrincipal authuser: InstagramUser,
    ): ResponseEntity<MutableList<RecentSearch>> {
        val recentSearchList = searchService.getRecentSearchList(authuser)
        return ResponseEntity.ok(recentSearchList)
    }

    // 최근 검색 기록 삭제
    @DeleteMapping("/recent/{searchId}")
    fun deleteRecentSearch(
        @AuthenticationPrincipal authuser: InstagramUser,
        @PathVariable("searchId") searchId: Long,
    ): ResponseEntity<MutableList<RecentSearch>> {
        val recentSearchList = searchService.removeRecentSearch(authuser, searchId)
        return ResponseEntity.ok(recentSearchList)
    }

    // 최근 검색 기록 모두 삭제
    @DeleteMapping("/recent/all")
    fun deleteAllRecentSearch(
        @AuthenticationPrincipal authuser: InstagramUser,
    ): ResponseEntity<Any> {
        searchService.removeAllRecentSearch(authuser)
        return ResponseEntity.ok(null)
    }
}
