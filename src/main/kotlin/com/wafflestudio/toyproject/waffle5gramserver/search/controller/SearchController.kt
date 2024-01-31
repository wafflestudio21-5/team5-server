package com.wafflestudio.toyproject.waffle5gramserver.search.controller

import com.wafflestudio.toyproject.waffle5gramserver.search.service.SearchService
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/search")
class SearchController(
    private val searchService: SearchService,
) {
}