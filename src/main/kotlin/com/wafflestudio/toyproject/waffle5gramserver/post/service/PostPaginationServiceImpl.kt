package com.wafflestudio.toyproject.waffle5gramserver.post.service

import com.wafflestudio.toyproject.waffle5gramserver.post.repository.PostCategory
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Slice
import org.springframework.stereotype.Service

@Service
class PostPaginationServiceImpl : PostPaginationService {
    override fun getRandomPosts(size: Int, category: PostCategory?): Slice<PostDetail> {
        TODO("Not yet implemented")
    }

    override fun getLatestPosts(page: Int, size: Int, category: PostCategory?): Slice<PostDetail> {
        TODO("Not yet implemented")
    }

    override fun getMostLikedPosts(page: Int, size: Int, category: PostCategory?): Slice<PostDetail> {
        TODO("Not yet implemented")
    }

    override fun getMostCommentedPosts(page: Int, size: Int, category: PostCategory?): Slice<PostDetail> {
        TODO("Not yet implemented")
    }
}