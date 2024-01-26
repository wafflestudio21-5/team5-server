package com.wafflestudio.toyproject.waffle5gramserver.feed.controller

import com.wafflestudio.toyproject.waffle5gramserver.feed.service.PostPreview
import com.wafflestudio.toyproject.waffle5gramserver.feed.service.SavedFeedService
import com.wafflestudio.toyproject.waffle5gramserver.post.service.PostDetail
import com.wafflestudio.toyproject.waffle5gramserver.user.service.InstagramUser
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/account/saved-feed")
class SavedFeedController(
    private val savedFeedService: SavedFeedService,
) {
    @GetMapping("/preview")
    fun getSavedFeedPreview(
        @AuthenticationPrincipal authuser: InstagramUser,
        @RequestParam(required = false) cursor: Long?,
        @RequestParam(defaultValue = "12") limit: Int,
    ): ResponseEntity<List<PostPreview>> {
        val postPreviews = savedFeedService.getSavedFeedPreview(authuser.username, cursor, limit)
        return ResponseEntity.ok(postPreviews)
    }

    @GetMapping("/newer")
    fun loadNewerPosts(
        @AuthenticationPrincipal authuser: InstagramUser,
        @RequestParam(required = false) cursor: Long?,
        @RequestParam(defaultValue = "10") limit: Int,
    ): ResponseEntity<List<PostDetail>> {
        val newerPosts = savedFeedService.loadNewerPosts(authuser.username, cursor, limit)
        return ResponseEntity.ok(newerPosts)
    }

    @GetMapping("/older")
    fun loadOlderPosts(
        @AuthenticationPrincipal authuser: InstagramUser,
        @RequestParam(required = false) cursor: Long?,
        @RequestParam(defaultValue = "10") limit: Int,
    ): ResponseEntity<List<PostDetail>> {
        val olderPosts = savedFeedService.loadOlderPosts(authuser.username, cursor, limit)
        return ResponseEntity.ok(olderPosts)
    }
}
