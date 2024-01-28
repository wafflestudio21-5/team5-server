package com.wafflestudio.toyproject.waffle5gramserver.explore.service

import com.wafflestudio.toyproject.waffle5gramserver.explore.dto.ExplorePostSortType
import com.wafflestudio.toyproject.waffle5gramserver.post.repository.PostCategory
import com.wafflestudio.toyproject.waffle5gramserver.post.service.PostDetail
import org.springframework.data.domain.Slice

interface ExploreService {
    fun getSlicedPosts(page: Int, size: Int, sortType: ExplorePostSortType, category: PostCategory?): Slice<PostDetail>
}
