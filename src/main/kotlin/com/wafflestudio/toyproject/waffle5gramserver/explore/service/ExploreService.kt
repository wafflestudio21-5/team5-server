package com.wafflestudio.toyproject.waffle5gramserver.explore.service

import com.wafflestudio.toyproject.waffle5gramserver.explore.dto.ExplorePostSortType
import com.wafflestudio.toyproject.waffle5gramserver.explore.dto.ExplorePreview
import com.wafflestudio.toyproject.waffle5gramserver.post.repository.PostCategory
import com.wafflestudio.toyproject.waffle5gramserver.post.service.PostDetail
import com.wafflestudio.toyproject.waffle5gramserver.post.service.PostMediasBrief
import com.wafflestudio.toyproject.waffle5gramserver.user.service.InstagramUser
import org.springframework.data.domain.Slice

interface ExploreService {
    fun getSlicedPosts(user: InstagramUser, page: Int, size: Int, sortType: ExplorePostSortType, category: PostCategory?): Slice<PostMediasBrief>

    fun getRandomSimpleSlicedPosts(user: InstagramUser, page: Int, size: Int, category: PostCategory?): Slice<ExplorePreview>

    fun getRandomDetailedSlicedPosts(user: InstagramUser, page: Int, size: Int): Slice<PostDetail>

    fun getOnePostById(user: InstagramUser, postId: Long): Slice<PostDetail>
}
