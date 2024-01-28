package com.wafflestudio.toyproject.waffle5gramserver.explore.service

import com.wafflestudio.toyproject.waffle5gramserver.explore.dto.ExplorePostSortType
import com.wafflestudio.toyproject.waffle5gramserver.post.repository.PostCategory
import com.wafflestudio.toyproject.waffle5gramserver.post.service.PostDetail
import com.wafflestudio.toyproject.waffle5gramserver.post.service.PostPaginationService
import org.springframework.data.domain.Slice
import org.springframework.stereotype.Service

@Service
class ExploreServiceImpl(
    private val postPaginationService: PostPaginationService
) : ExploreService {

    override fun getSlicedPosts(
        page: Int,
        size: Int,
        sortType: ExplorePostSortType,
        category: PostCategory?
    ): Slice<PostDetail> {
        return when (sortType) {
            ExplorePostSortType.RANDOM -> postPaginationService.getRandomPosts(size, category)
            ExplorePostSortType.LATEST -> postPaginationService.getLatestPosts(page, size, category)
            ExplorePostSortType.MOST_LIKED -> postPaginationService.getMostLikedPosts(page, size, category)
            ExplorePostSortType.MOST_COMMENTED -> postPaginationService.getMostCommentedPosts(page, size, category)
        }
    }
}