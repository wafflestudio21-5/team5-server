package com.wafflestudio.toyproject.waffle5gramserver.feed.controller

import com.wafflestudio.toyproject.waffle5gramserver.feed.service.PostPreview
import com.wafflestudio.toyproject.waffle5gramserver.feed.service.UserFeedService
import com.wafflestudio.toyproject.waffle5gramserver.post.service.PostDetail
import com.wafflestudio.toyproject.waffle5gramserver.user.service.InstagramUser
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/account/{username}/feed")
class UserFeedController(
    private val userFeedService: UserFeedService,
) {
    // 1. 유저의 게시물 미리보기 조회 API
    @GetMapping("/preview")
    fun getUserFeedPreview(
        @AuthenticationPrincipal authuser: InstagramUser,
        @PathVariable username: String,
        @RequestParam(required = false) cursor: Long?,
        @RequestParam(defaultValue = "12") limit: Int,
    ): ResponseEntity<List<PostPreview>> {
        val postPreviews = userFeedService.getUserFeedPreview(authuser, username, cursor, limit)
        return ResponseEntity.ok(postPreviews)
    }

    // 2. 무한 스크롤을 통한 게시물 조회 API
    // 위로 스크롤하여 최신 게시물 로드
    @GetMapping("/newer")
    fun loadNewerPosts(
        @AuthenticationPrincipal authuser: InstagramUser,
        @PathVariable username: String,
        @RequestParam(required = false) cursor: Long?,
        @RequestParam(defaultValue = "10") limit: Int,
    ): ResponseEntity<List<PostDetail>> {
        val newerPosts = userFeedService.loadNewerPosts(authuser, username, cursor, limit)
        return ResponseEntity.ok(newerPosts)
    }

    // 아래로 스크롤하여 이전 게시물 로드
    @GetMapping("/older")
    fun loadOlderPosts(
        @AuthenticationPrincipal authuser: InstagramUser,
        @PathVariable username: String,
        @RequestParam(required = false) cursor: Long?,
        @RequestParam(defaultValue = "10") limit: Int,
    ): ResponseEntity<List<PostDetail>> {
        val olderPosts = userFeedService.loadOlderPosts(authuser, username, cursor, limit)
        return ResponseEntity.ok(olderPosts)
    }
}
