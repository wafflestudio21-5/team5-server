package com.wafflestudio.toyproject.waffle5gramserver.feed

import com.wafflestudio.toyproject.waffle5gramserver.feed.service.FeedService
import com.wafflestudio.toyproject.waffle5gramserver.post.repository.PostRepository
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class FeedIntegrationTest @Autowired constructor(
    private val feedService: FeedService,
    private val postRepository: PostRepository,
) {
    @Test
    fun `test`() {
        println("test")
    }
}