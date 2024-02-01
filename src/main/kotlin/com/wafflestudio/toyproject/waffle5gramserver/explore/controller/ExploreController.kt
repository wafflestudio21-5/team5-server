package com.wafflestudio.toyproject.waffle5gramserver.explore.controller

import com.wafflestudio.toyproject.waffle5gramserver.explore.dto.ExploreFeedResponseDto
import com.wafflestudio.toyproject.waffle5gramserver.explore.dto.ExploreQueryDto
import com.wafflestudio.toyproject.waffle5gramserver.explore.dto.ExploreResponseDto
import com.wafflestudio.toyproject.waffle5gramserver.explore.service.ExploreService
import com.wafflestudio.toyproject.waffle5gramserver.feed.controller.PageInfo
import com.wafflestudio.toyproject.waffle5gramserver.user.service.InstagramUser
import org.springframework.data.domain.Slice
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
                pageInfo = getDefaultPageInfo(slicedExplorePreview)
            )
        )
    }

    @GetMapping("/api/v1/explore/{postId}")
    fun getFeeds(
        @PathVariable postId: Long,
        @RequestParam(name = "page", required = false) page: Int = 1,
        @RequestParam(name = "size", required = false) size: Int = 6,
        @AuthenticationPrincipal user: InstagramUser
    ): ResponseEntity<ExploreFeedResponseDto> {
        if (page == 1) {
            val firstPostDetail = exploreService.getOnePostById(user, postId)
            if (size == 1) {
                return ResponseEntity.ok(
                    ExploreFeedResponseDto(
                        posts = firstPostDetail.content,
                        pageInfo = PageInfo(
                            page = 1,
                            size = 1,
                            offset = firstPostDetail.pageable.offset,
                            hasNext = firstPostDetail.hasNext(),
                            elements = firstPostDetail.numberOfElements
                        )
                    )
                )
            } else {
                val randomPostDetails = exploreService.getRandomDetailedSlicedPosts(
                    user = user,
                    page = page,
                    size = size - 1
                )
                val postDetails = mutableListOf(
                    firstPostDetail.content[0]
                )
                randomPostDetails.content.shuffled().map {
                    postDetails.add(it)
                }
                return ResponseEntity.ok(
                    ExploreFeedResponseDto(
                        posts = postDetails,
                        pageInfo = PageInfo(
                            page = 1,
                            size = size,
                            offset = 0,
                            hasNext = randomPostDetails.hasNext(),
                            elements = firstPostDetail.numberOfElements + randomPostDetails.numberOfElements
                        )
                    )
                )
            }
        }
        // page is not one.
        val slicedPostDetails = exploreService.getRandomDetailedSlicedPosts(
            user = user,
            page = page,
            size = size
        )
        return ResponseEntity.ok(
            ExploreFeedResponseDto(
                posts = slicedPostDetails.content.shuffled(),
                pageInfo = getDefaultPageInfo(slicedPostDetails)
            )
        )
    }

    private fun <T> getDefaultPageInfo(slice: Slice<T>): PageInfo {
        return PageInfo(
            page = slice.number + 1,
            size = slice.size,
            offset = slice.pageable.offset,
            hasNext = slice.hasNext(),
            elements = slice.numberOfElements
        )
    }
}
