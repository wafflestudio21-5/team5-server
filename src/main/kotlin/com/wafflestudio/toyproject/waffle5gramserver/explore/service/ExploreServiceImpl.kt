package com.wafflestudio.toyproject.waffle5gramserver.explore.service

import com.wafflestudio.toyproject.waffle5gramserver.explore.dto.ExplorePostSortType
import com.wafflestudio.toyproject.waffle5gramserver.post.mapper.PostMapper
import com.wafflestudio.toyproject.waffle5gramserver.post.repository.PostCategory
import com.wafflestudio.toyproject.waffle5gramserver.post.service.PostMediasBrief
import com.wafflestudio.toyproject.waffle5gramserver.post.service.PostPaginationService
import com.wafflestudio.toyproject.waffle5gramserver.user.service.InstagramUser
import org.springframework.data.domain.Slice
import org.springframework.stereotype.Service

@Service
class ExploreServiceImpl(
    private val postPaginationService: PostPaginationService
) : ExploreService {

    override fun getSlicedPosts(
        user: InstagramUser,
        page: Int,
        size: Int,
        sortType: ExplorePostSortType,
        category: PostCategory?
    ): Slice<PostMediasBrief> {
        val postEntities = when (sortType) {
            ExplorePostSortType.RANDOM -> postPaginationService.getRandomPosts(user, size, category)
            ExplorePostSortType.LATEST -> postPaginationService.getLatestPosts(user, page, size, category)
            ExplorePostSortType.MOST_LIKED -> postPaginationService.getMostLikedPosts(user, page, size, category)
            ExplorePostSortType.MOST_COMMENTED -> postPaginationService.getMostCommentedPosts(user, page, size, category)
        }
        return postEntities.map {
            PostMapper.toPostMediasBrief(it)
        }
    }
}
