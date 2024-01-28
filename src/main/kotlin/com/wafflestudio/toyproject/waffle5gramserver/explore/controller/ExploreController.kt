package com.wafflestudio.toyproject.waffle5gramserver.explore.controller

import com.wafflestudio.toyproject.waffle5gramserver.explore.dto.ExploreQueryDto
import com.wafflestudio.toyproject.waffle5gramserver.user.service.InstagramUser
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class ExploreController {
    @GetMapping("/api/v1/explore")
    fun getPosts(
        @RequestParam exploreQueryDto: ExploreQueryDto,
        @AuthenticationPrincipal user: InstagramUser
    ) {

    }
}