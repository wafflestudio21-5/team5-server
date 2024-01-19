package com.wafflestudio.toyproject.waffle5gramserver.feed.service

import com.wafflestudio.toyproject.waffle5gramserver.post.mapper.PostMapper
import com.wafflestudio.toyproject.waffle5gramserver.post.repository.PostRepository
import com.wafflestudio.toyproject.waffle5gramserver.post.service.PostDetail
import com.wafflestudio.toyproject.waffle5gramserver.post.service.PostLikeService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class FeedServiceImpl(
    private val postRepository: PostRepository,
    private val postLikeService: PostLikeService
) : FeedService {

    @Transactional(readOnly = true)
    override fun getHomeFeed(userId: Long, pageable: Pageable): Page<PostDetail> {
        val postsList = postRepository.findAllByUserIdIsNotOrderByCreatedAtDesc(userId, pageable)

        val postDetailsList = postsList.map { post ->
            val liked = postLikeService.exists(post.id, userId)
            PostMapper.toPostDetailDTO(post, liked)
        }

        return postDetailsList
    }

    override fun getFeedWithPostId(postId: Long): List<PostDetail> {
        TODO("Not yet implemented")
    }
}
