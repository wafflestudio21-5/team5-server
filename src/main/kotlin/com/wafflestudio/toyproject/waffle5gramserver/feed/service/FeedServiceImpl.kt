package com.wafflestudio.toyproject.waffle5gramserver.feed.service

import com.wafflestudio.toyproject.waffle5gramserver.feed.repository.FeedQueryRepository
import com.wafflestudio.toyproject.waffle5gramserver.post.mapper.PostMapper
import com.wafflestudio.toyproject.waffle5gramserver.post.repository.PostLikeRepository
import com.wafflestudio.toyproject.waffle5gramserver.post.repository.PostSaveRepository
import com.wafflestudio.toyproject.waffle5gramserver.post.service.PostDetail
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Slice
import org.springframework.data.domain.SliceImpl
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class FeedServiceImpl(
    private val postSaveRepository: PostSaveRepository,
    private val postLikeRepository: PostLikeRepository,
    private val feedQueryRepository: FeedQueryRepository,
) : FeedService {
    @Transactional(readOnly = true)
    override fun getHomeFeed(
        userId: Long,
        pageable: Pageable,
    ): Slice<PostDetail> {
        val postsList = feedQueryRepository.findPostsNotCreatedByAndNotLikedByUser(userId, pageable)

        val sliceInfo = postsList.pageable
        val postDetailsList =
            postsList.map { post ->
                val isLiked = postLikeRepository.findByPostIdAndUserId(post.id, userId) != null
                val isSaved = postSaveRepository.findByPostIdAndUserId(post.id, userId) != null
                PostMapper.toPostDetailDTO(post, isLiked, isSaved)
            }.toList()

        val shuffledPostDetailsList = postDetailsList.shuffled()

        return SliceImpl(
            shuffledPostDetailsList,
            sliceInfo,
            postsList.hasNext(),
        )
    }

    override fun getFeedWithPostId(postId: Long): List<PostDetail> {
        TODO("Not yet implemented")
    }
}
