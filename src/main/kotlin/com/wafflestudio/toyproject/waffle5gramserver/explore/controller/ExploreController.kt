package com.wafflestudio.toyproject.waffle5gramserver.explore.controller

import com.wafflestudio.toyproject.waffle5gramserver.explore.dto.ExploreFeedResponseDto
import com.wafflestudio.toyproject.waffle5gramserver.explore.dto.ExploreQueryDto
import com.wafflestudio.toyproject.waffle5gramserver.explore.dto.ExploreResponseDto
import com.wafflestudio.toyproject.waffle5gramserver.explore.service.ExploreService
import com.wafflestudio.toyproject.waffle5gramserver.feed.controller.PageInfo
import com.wafflestudio.toyproject.waffle5gramserver.user.service.InstagramUser
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class ExploreController(
    private val exploreService: ExploreService
) {
    @GetMapping("/api/v1/explore")
    fun getPosts(
        exploreQueryDto: ExploreQueryDto,
        @AuthenticationPrincipal user: InstagramUser
    ): ResponseEntity<ExploreResponseDto> {
        val slicedExplorePreview = exploreService.getRandomSimpleSlicedPosts(
            user = user,
            page = exploreQueryDto.page,
            size = exploreQueryDto.size,
            category = exploreQueryDto.category,
        )
        return ResponseEntity.ok(
            ExploreResponseDto(
                previews = slicedExplorePreview.content,
                pageInfo = PageInfo(
                    page = slicedExplorePreview.number,
                    size = slicedExplorePreview.size,
                    offset = slicedExplorePreview.pageable.offset,
                    hasNext = slicedExplorePreview.hasNext(),
                    elements = slicedExplorePreview.numberOfElements
                )
            )
        )
    }

    @GetMapping("/api/v1/explore/{postId}")
    fun getFeeds(
        @PathVariable postId: String,
        @RequestParam(name = "page", required = false) page: Int = 0,
        @RequestParam(name = "size", required = false) size: Int = 6,
        @AuthenticationPrincipal user: InstagramUser
    ): ResponseEntity<ExploreFeedResponseDto> {
        val slicedPostDetails = exploreService.getRandomDetailedSlicedPosts(
            user = user,
            page = page,
            size = size
        )
        return ResponseEntity.ok(
            ExploreFeedResponseDto(
                posts = slicedPostDetails.content.shuffled(),
                pageInfo = PageInfo(
                    page = slicedPostDetails.number,
                    size = slicedPostDetails.size,
                    offset = slicedPostDetails.pageable.offset,
                    hasNext = slicedPostDetails.hasNext(),
                    elements = slicedPostDetails.numberOfElements
                )
            )
        )
    }
}
