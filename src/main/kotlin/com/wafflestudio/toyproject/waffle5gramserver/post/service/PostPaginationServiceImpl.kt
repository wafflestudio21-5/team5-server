package com.wafflestudio.toyproject.waffle5gramserver.post.service

import com.wafflestudio.toyproject.waffle5gramserver.post.repository.PostCategory
import com.wafflestudio.toyproject.waffle5gramserver.post.repository.PostEntity
import com.wafflestudio.toyproject.waffle5gramserver.post.repository.PostRepository
import com.wafflestudio.toyproject.waffle5gramserver.user.service.InstagramUser
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Slice
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service

@Service
class PostPaginationServiceImpl(
    private val postRepository: PostRepository,
) : PostPaginationService {
    override fun getRandomPosts(
        user: InstagramUser,
        size: Int,
        category: PostCategory?
    ): Slice<PostEntity> {
        TODO("Not yet implemented")
    }

    override fun getLatestPosts(
        user: InstagramUser,
        page: Int,
        size: Int,
        category: PostCategory?
    ): Slice<PostEntity> {
        val pageable = PageRequest.of(page, size, Sort.by("createdAt").descending())
        return if (category == null) {
            postRepository.findSlice(pageable)
        } else {
            postRepository.findSliceByCategory(pageable, category)
        }
    }

    override fun getMostLikedPosts(
        user: InstagramUser,
        page: Int,
        size: Int,
        category: PostCategory?
    ): Slice<PostEntity> {
        val pageable = PageRequest.of(page, size, Sort.by("likeCount").descending())
        return if (category == null) {
            postRepository.findSliceLikeCountDisplayed(pageable)
        } else {
            postRepository.findSliceLikeCountDisplayedByCategory(pageable, category)
        }
    }

    override fun getMostCommentedPosts(
        user: InstagramUser,
        page: Int,
        size: Int,
        category: PostCategory?
    ): Slice<PostEntity> {
        val pageable = PageRequest.of(page, size)
        return if (category == null) {
            postRepository.findSliceCommentDisplayedOrderByCommentCountDesc(pageable)
        } else {
            postRepository.findSliceCommentDisplayedOrderByCommentCountDescByCategory(pageable, category)
        }
    }
}
