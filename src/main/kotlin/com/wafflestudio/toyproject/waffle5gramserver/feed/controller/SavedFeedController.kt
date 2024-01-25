package com.wafflestudio.toyproject.waffle5gramserver.feed.controller

import com.wafflestudio.toyproject.waffle5gramserver.feed.service.PostPreview
import com.wafflestudio.toyproject.waffle5gramserver.feed.service.SavedFeedService
import com.wafflestudio.toyproject.waffle5gramserver.post.service.PostDetail
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/account/{username}/saved-feed")
class SavedFeedController(
    private val savedFeedService: SavedFeedService,
) {
    @GetMapping("/preview")
    fun getSavedFeedPreview(
        @PathVariable username: String,
        @RequestParam(required = false) cursor: Long?,
        @RequestParam(defaultValue = "12") limit: Int,
    ): ResponseEntity<List<PostPreview>> {
        val postPreviews = savedFeedService.getSavedFeedPreview(username, cursor, limit)
        return ResponseEntity.ok(postPreviews)
    }

    @GetMapping("/newer")
    fun loadNewerPosts(
        @PathVariable username: String,
        @RequestParam(required = false) cursor: Long?,
        @RequestParam(defaultValue = "10") limit: Int,
    ): ResponseEntity<List<PostDetail>> {
        val newerPosts = savedFeedService.loadNewerPosts(username, cursor, limit)
        return ResponseEntity.ok(newerPosts)
    }

    @GetMapping("/older")
    fun loadOlderPosts(
        @PathVariable username: String,
        @RequestParam(required = false) cursor: Long?,
        @RequestParam(defaultValue = "10") limit: Int,
    ): ResponseEntity<List<PostDetail>> {
        val olderPosts = savedFeedService.loadOlderPosts(username, cursor, limit)
        return ResponseEntity.ok(olderPosts)
    }
}
