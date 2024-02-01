package com.wafflestudio.toyproject.waffle5gramserver.feed.controller

import com.wafflestudio.toyproject.waffle5gramserver.feed.service.FeedService
import com.wafflestudio.toyproject.waffle5gramserver.post.service.PostDetail
import com.wafflestudio.toyproject.waffle5gramserver.user.service.InstagramUser
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Slice
import org.springframework.data.domain.Sort
import org.springframework.data.web.PageableDefault
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
    private val feedService: FeedService,
) {
    @GetMapping("/timeline")
    fun getFeedWithRecommendation(
        @AuthenticationPrincipal user: InstagramUser,
        @PageableDefault(size = 10, sort = ["createdAt"], direction = Sort.Direction.DESC) pageable: Pageable,
    ): ResponseEntity<Any> {
        val postsPage: Slice<PostDetail> = feedService.getHomeFeed(userId = user.id, pageable = pageable)

        val feedResponse =
            FeedResponse(
                posts = postsPage.content,
                pageInfo =
                    PageInfo(
                        page = postsPage.number + 1,
                        size = postsPage.size,
                        offset = postsPage.pageable.offset,
                        hasNext = postsPage.hasNext(),
                        elements = postsPage.numberOfElements,
                    ),
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
