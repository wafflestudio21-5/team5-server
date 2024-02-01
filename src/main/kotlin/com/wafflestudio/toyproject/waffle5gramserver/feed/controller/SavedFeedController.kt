package com.wafflestudio.toyproject.waffle5gramserver.feed.controller

import com.wafflestudio.toyproject.waffle5gramserver.feed.service.SavedFeedService
import com.wafflestudio.toyproject.waffle5gramserver.post.service.PostDetail
import com.wafflestudio.toyproject.waffle5gramserver.user.service.InstagramUser
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Slice
import org.springframework.data.web.PageableDefault
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
        @PageableDefault(size = 12) pageable: Pageable,
    ): ResponseEntity<Any> {
        val postPreviews = savedFeedService.getSavedFeedPreview(authuser.id, pageable)
        val feedPreviewResponse =
            FeedPreviewResponse(
                posts = postPreviews.content,
                pageInfo =
                PageInfo(
                    page = pageable.pageNumber + 1,
                    size = pageable.pageSize,
                    offset = pageable.offset,
                    hasNext = postPreviews.size == pageable.pageSize,
                    elements = postPreviews.size,
                ),
            )
        return ResponseEntity.ok(feedPreviewResponse)
    }

    // 피드 조회 API
    @GetMapping("")
    fun getSavedFeed(
        @AuthenticationPrincipal authuser: InstagramUser,
        @PageableDefault(size = 10) pageable: Pageable,
    ): ResponseEntity<Any> {
        val postsPage: Slice<PostDetail> = savedFeedService.getSavedFeed(authuser.id, pageable)

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
}
