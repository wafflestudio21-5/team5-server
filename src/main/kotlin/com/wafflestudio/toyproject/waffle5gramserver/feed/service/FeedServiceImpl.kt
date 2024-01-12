package com.wafflestudio.toyproject.waffle5gramserver.feed.service

import com.wafflestudio.toyproject.waffle5gramserver.post.mapper.PostMapper
import com.wafflestudio.toyproject.waffle5gramserver.post.repository.PostEntity
import com.wafflestudio.toyproject.waffle5gramserver.post.repository.PostLikeRepository
import com.wafflestudio.toyproject.waffle5gramserver.post.repository.PostRepository
import com.wafflestudio.toyproject.waffle5gramserver.post.service.PostDetail
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class FeedServiceImpl (
    private val postRepository: PostRepository,
    private val postLikeRepository: PostLikeRepository
) : FeedService {

    override fun getHomeFeed(userId: Long, pageable: Pageable): Page<PostDetail> {
        val posts = postRepository.findByUserIdOrderByCreatedAtDesc(userId, pageable)
        return posts.map { post:PostEntity ->
            val liked = postLikeRepository.countByPostIdAndUserId(post.id, userId) > 0
            PostMapper.toPostDetailDTO(post, false)
        }
    }

    override fun getFeedWithPostId(postId: Long): List<PostDetail> {
        TODO("Not yet implemented")
    }

}