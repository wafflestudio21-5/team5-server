package com.wafflestudio.toyproject.waffle5gramserver.feed.controller

import com.wafflestudio.toyproject.waffle5gramserver.feed.service.FeedService
import com.wafflestudio.toyproject.waffle5gramserver.post.service.PostDetail
import com.wafflestudio.toyproject.waffle5gramserver.user.service.InstagramUser
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/feed")
class FeedController(
    private val feedService: FeedService
) {
    @GetMapping("/timeline")
    fun getFeedWithRecommendation(
        @AuthenticationPrincipal user: InstagramUser,
        @RequestParam("page", defaultValue = "1") page: Int,
        @RequestParam("size", defaultValue = "10") size: Int,
    ): ResponseEntity<Any> {
        val pageable: Pageable = PageRequest.of(page - 1, size)
        val postsPage: Page<PostDetail> = feedService.getHomeFeed(userId = user.id, pageable = pageable)

        val feedResponse = FeedResponse(
            posts = postsPage.content,
            pageInfo = PageInfo(
                currentPage = postsPage.number + 1,
                pageSize = postsPage.size,
                totalItems = postsPage.totalElements,
                totalPages = postsPage.totalPages
            )
        )

        return ResponseEntity.ok(feedResponse)
    }

    @GetMapping("/{postId}")
    fun getFeedWithPostId(
        @AuthenticationPrincipal user: InstagramUser,
        @PathVariable("postId") postId: Long,
        @RequestParam("page", defaultValue = "1") page: Int,
        @RequestParam("size", defaultValue = "10") size: Int,
    ) {
        TODO()
    }
}

data class FeedResponse(
    val posts: List<PostDetail>,
    val pageInfo: PageInfo
)
