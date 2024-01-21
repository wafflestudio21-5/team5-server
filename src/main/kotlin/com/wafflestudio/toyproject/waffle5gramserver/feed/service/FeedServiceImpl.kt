package com.wafflestudio.toyproject.waffle5gramserver.feed.service

import com.wafflestudio.toyproject.waffle5gramserver.feed.repository.FeedQueryRepository
import com.wafflestudio.toyproject.waffle5gramserver.post.mapper.PostMapper
import com.wafflestudio.toyproject.waffle5gramserver.post.repository.PostRepository
import com.wafflestudio.toyproject.waffle5gramserver.post.service.PostDetail
import com.wafflestudio.toyproject.waffle5gramserver.post.service.PostLikeService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Slice
import org.springframework.data.domain.SliceImpl
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class FeedServiceImpl(
    private val postRepository: PostRepository,
    private val postLikeService: PostLikeService,
    private val feedQueryRepository: FeedQueryRepository
) : FeedService {

    @Transactional(readOnly = true)
    override fun getHomeFeed(userId: Long, pageable: Pageable): Slice<PostDetail> {
        val postsList = feedQueryRepository.findPostsNotCreatedByAndNotLikedByUser(userId, pageable)

        val sliceInfo = postsList.pageable
        val postDetailsList = postsList.map { post ->
            PostMapper.toPostDetailDTO(post)
        }.toList()

        val shuffledPostDetailsList = postDetailsList.shuffled()

        return SliceImpl(
            shuffledPostDetailsList,
            sliceInfo,
            postsList.hasNext()
        )
    }

    override fun getFeedWithPostId(postId: Long): List<PostDetail> {
        TODO("Not yet implemented")
    }
}
