package com.wafflestudio.toyproject.waffle5gramserver.post.service

import com.wafflestudio.toyproject.waffle5gramserver.post.repository.PostCategory
import com.wafflestudio.toyproject.waffle5gramserver.post.repository.PostEntity
import com.wafflestudio.toyproject.waffle5gramserver.user.service.InstagramUser
import org.springframework.data.domain.Slice

interface PostPaginationService {
    fun getRandomPosts(user: InstagramUser, size: Int, category: PostCategory?): Slice<PostEntity>
    fun getLatestPosts(user: InstagramUser, page: Int, size: Int, category: PostCategory?): Slice<PostEntity>
    fun getMostLikedPosts(user: InstagramUser, page: Int, size: Int, category: PostCategory?): Slice<PostEntity>
    fun getMostCommentedPosts(user: InstagramUser, page: Int, size: Int, category: PostCategory?): Slice<PostEntity>
}
