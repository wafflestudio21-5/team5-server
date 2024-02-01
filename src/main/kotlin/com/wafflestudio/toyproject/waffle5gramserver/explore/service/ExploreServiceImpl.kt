package com.wafflestudio.toyproject.waffle5gramserver.explore.service

import com.wafflestudio.toyproject.waffle5gramserver.explore.dto.ExplorePostSortType
import com.wafflestudio.toyproject.waffle5gramserver.explore.dto.ExplorePreview
import com.wafflestudio.toyproject.waffle5gramserver.post.mapper.PostMapper
import com.wafflestudio.toyproject.waffle5gramserver.post.repository.PostCategory
import com.wafflestudio.toyproject.waffle5gramserver.post.repository.PostRepository
import com.wafflestudio.toyproject.waffle5gramserver.post.service.PostDetail
import com.wafflestudio.toyproject.waffle5gramserver.post.service.PostMediasBrief
import com.wafflestudio.toyproject.waffle5gramserver.post.service.PostPaginationService
import com.wafflestudio.toyproject.waffle5gramserver.user.service.InstagramUser
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Slice
import org.springframework.stereotype.Service

@Service
class ExploreServiceImpl(
    private val postPaginationService: PostPaginationService,
    private val postRepository: PostRepository
) : ExploreService {

    override fun getSlicedPosts(
        user: InstagramUser,
        page: Int,
        size: Int,
        sortType: ExplorePostSortType,
        category: PostCategory?
    ): Slice<PostMediasBrief> {
        return when (sortType) {
            ExplorePostSortType.RANDOM ->
                postPaginationService.getRandomPosts(user, size, category).map {
                    PostMapper.toPostMediasBrief(it)
                }
            ExplorePostSortType.LATEST ->
                postPaginationService.getLatestPosts(user, page, size, category).map {
                    PostMapper.toPostMediasBrief(it)
                }
            ExplorePostSortType.MOST_LIKED ->
                postPaginationService.getMostLikedPosts(user, page, size, category).map {
                    PostMapper.toPostMediasBrief(it)
                }
            ExplorePostSortType.MOST_COMMENTED ->
                postPaginationService.getMostCommentedPosts(user, page, size, category).map {
                    PostMapper.toPostMediasBrief(it.getPostEntity(), it.getCommentCount())
                }
        }
    }

    override fun getRandomSimpleSlicedPosts(
        user: InstagramUser,
        page: Int,
        size: Int,
        category: PostCategory?
    ): Slice<ExplorePreview> {
        return postPaginationService.getRandomPosts(user, size, category).map {
            PostMapper.toExplorePreview(it)
        }
    }

    override fun getRandomDetailedSlicedPosts(
        user: InstagramUser,
        page: Int,
        size: Int,
    ): Slice<PostDetail> {
        return postRepository.findSlicedPostDetails(
            pageable = PageRequest.of(page, size),
            currentUserId = user.id
        ).map {
            PostMapper.toPostMediaDetail(it)
        }
    }

    override fun getPostById(user: InstagramUser, postId: Long): PostDetail {
        TODO("Not yet implemented")
    }
}
