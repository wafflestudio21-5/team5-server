package com.wafflestudio.toyproject.waffle5gramserver.explore.controller

import com.wafflestudio.toyproject.waffle5gramserver.explore.dto.ExploreQueryDto
import com.wafflestudio.toyproject.waffle5gramserver.explore.service.ExploreService
import com.wafflestudio.toyproject.waffle5gramserver.post.service.PostMediasBrief
import com.wafflestudio.toyproject.waffle5gramserver.user.service.InstagramUser
import org.springframework.data.domain.Slice
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class ExploreController(
    private val exploreService: ExploreService
) {
    @GetMapping("/api/v1/explore")
    fun getPosts(
        exploreQueryDto: ExploreQueryDto,
        @AuthenticationPrincipal user: InstagramUser
    ): ResponseEntity<Slice<PostMediasBrief>> {
        val slicedPosts = exploreService.getSlicedPosts(
            user = user,
            page = exploreQueryDto.page,
            size = exploreQueryDto.size,
            sortType = exploreQueryDto.sort,
            category = exploreQueryDto.category,
        )
        return ResponseEntity.ok(slicedPosts)
    }
}
