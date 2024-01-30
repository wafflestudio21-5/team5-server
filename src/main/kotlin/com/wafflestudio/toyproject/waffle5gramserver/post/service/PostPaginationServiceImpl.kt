package com.wafflestudio.toyproject.waffle5gramserver.post.service

import com.wafflestudio.toyproject.waffle5gramserver.post.repository.PostCategory
import com.wafflestudio.toyproject.waffle5gramserver.post.repository.PostEntity
import com.wafflestudio.toyproject.waffle5gramserver.post.repository.PostRepository
import com.wafflestudio.toyproject.waffle5gramserver.user.service.InstagramUser
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Slice
import org.springframework.data.domain.SliceImpl
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import kotlin.random.Random

@Service
class PostPaginationServiceImpl(
    private val postRepository: PostRepository,
) : PostPaginationService {

    private val minPagesRandomApplied = 3

    override fun getRandomPosts(
        user: InstagramUser,
        size: Int,
        category: PostCategory?
    ): Slice<PostEntity> {
        val totalPages = postRepository.count().toInt() / size + 1
        val pageable = if (totalPages >= minPagesRandomApplied) {
            val randomPage = Random.nextInt(0, totalPages - 1)
            PageRequest.of(randomPage, size)
        } else {
            PageRequest.of(0, size)
        }
        val postEntities = if (category == null) {
            postRepository.findSlice(pageable)
        } else {
            postRepository.findSliceByCategory(pageable, category)
        }
        return shuffleSlice(postEntities)
    }

    private fun shuffleSlice(originalSlice: Slice<PostEntity>): Slice<PostEntity> {
        val contents = originalSlice.content.toMutableList().shuffled()
        return SliceImpl(contents, originalSlice.pageable, originalSlice.hasNext())
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
    ): Slice<PostEntityWithCommentCount> {
        val pageable = PageRequest.of(page, size)
        return if (category == null) {
            postRepository.findSliceCommentDisplayedOrderByCommentCountDesc(pageable)
        } else {
            postRepository.findSliceCommentDisplayedOrderByCommentCountDescByCategory(pageable, category)
        }
    }
}
