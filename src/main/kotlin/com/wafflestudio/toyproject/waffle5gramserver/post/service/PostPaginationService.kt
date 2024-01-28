package com.wafflestudio.toyproject.waffle5gramserver.post.service

import com.wafflestudio.toyproject.waffle5gramserver.post.repository.PostCategory
import org.springframework.data.domain.Slice

interface PostPaginationService {
    fun getRandomPosts(size: Int, category: PostCategory?): Slice<PostDetail>
    fun getLatestPosts(page: Int, size: Int, category: PostCategory?): Slice<PostDetail>
    fun getMostLikedPosts(page: Int, size: Int, category: PostCategory?): Slice<PostDetail>
    fun getMostCommentedPosts(page: Int, size: Int, category: PostCategory?): Slice<PostDetail>
}