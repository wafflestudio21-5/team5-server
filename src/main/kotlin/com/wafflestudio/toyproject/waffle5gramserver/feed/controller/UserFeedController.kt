package com.wafflestudio.toyproject.waffle5gramserver.feed.controller

import com.wafflestudio.toyproject.waffle5gramserver.feed.service.UserFeedService
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
        @PageableDefault(size = 12, sort = ["createdAt"], direction = Sort.Direction.DESC) pageable: Pageable,
    ): ResponseEntity<Any> {
        val postPreviews = userFeedService.getUserFeedPreview(authuser, username, pageable)

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
    fun getUserFeed(
        @AuthenticationPrincipal authuser: InstagramUser,
        @PathVariable username: String,
        @PageableDefault(size = 10, sort = ["createdAt"], direction = Sort.Direction.DESC) pageable: Pageable,
    ): ResponseEntity<Any> {
        val postsPage: Slice<PostDetail> = userFeedService.getUserFeed(authuser, username, pageable)

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
