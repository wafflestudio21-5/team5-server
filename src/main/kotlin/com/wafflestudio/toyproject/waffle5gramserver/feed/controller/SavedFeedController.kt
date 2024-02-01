package com.wafflestudio.toyproject.waffle5gramserver.feed.controller

import com.wafflestudio.toyproject.waffle5gramserver.feed.service.SavedFeedService
import com.wafflestudio.toyproject.waffle5gramserver.post.service.PostDetail
import com.wafflestudio.toyproject.waffle5gramserver.user.service.InstagramUser
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/account/saved-feed")
class SavedFeedController(
    private val savedFeedService: SavedFeedService,
) {
    @GetMapping("/preview")
    fun getSavedFeedPreview(
        @AuthenticationPrincipal authuser: InstagramUser,
    ): ResponseEntity<List<PostPreview>> {
        val postPreviews = savedFeedService.getSavedFeedPreview(authuser.id)
        return ResponseEntity.ok(postPreviews)

    }

    // 피드 조회 API
    @GetMapping("")
    fun getSavedFeed(
        @AuthenticationPrincipal authuser: InstagramUser,
    ): ResponseEntity<List<PostDetail>> {
        val posts = savedFeedService.getSavedFeed(authuser.id)
        return ResponseEntity.ok(posts)
    }
}
