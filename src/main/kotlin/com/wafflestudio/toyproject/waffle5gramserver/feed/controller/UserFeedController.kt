package com.wafflestudio.toyproject.waffle5gramserver.feed.controller

import com.wafflestudio.toyproject.waffle5gramserver.feed.service.PostPreview
import com.wafflestudio.toyproject.waffle5gramserver.feed.service.UserFeedService
import com.wafflestudio.toyproject.waffle5gramserver.post.service.PostDetail
import com.wafflestudio.toyproject.waffle5gramserver.user.service.InstagramUser
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.web.PageableDefault
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
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
    ): ResponseEntity<List<PostPreview>> {
        val postPreviews = userFeedService.getUserFeedPreview(authuser, username)
        return ResponseEntity.ok(postPreviews)
    }

    // 피드 조회 API
    @GetMapping("")
    fun getUserFeed(
        @AuthenticationPrincipal authuser: InstagramUser,
        @PathVariable username: String,
    ): ResponseEntity<List<PostDetail>> {
        val posts = userFeedService.getUserFeed(authuser, username)
        return ResponseEntity.ok(posts)
    }
}
