package com.wafflestudio.toyproject.waffle5gramserver.feed.service

import com.wafflestudio.toyproject.waffle5gramserver.post.mapper.PostMapper
import com.wafflestudio.toyproject.waffle5gramserver.post.repository.PostLikeRepository
import com.wafflestudio.toyproject.waffle5gramserver.post.repository.PostRepository
import com.wafflestudio.toyproject.waffle5gramserver.post.repository.PostSaveRepository
import com.wafflestudio.toyproject.waffle5gramserver.post.service.PostDetail
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service

@Service
class UserFeedServiceImpl(
    private val postRepository: PostRepository,
    private val postLikeRepository: PostLikeRepository,
    private val postSaveRepository: PostSaveRepository,
) : UserFeedService {
    override fun getUserFeedPreview(
        userId: Long,
        cursor: Long?,
        limit: Int,
    ): List<PostPreview> {
        val pageable = PageRequest.of(0, limit)
        val posts =
            if (cursor == null) {
                postRepository.findLatestPostsByUserId(userId, pageable)
            } else {
                postRepository.findPostsByUserIdAndCursor(userId, cursor, pageable)
            }

        return posts.map { post ->
            PostPreview(
                id = post.id,
                thumbnailUrl = post.medias.firstOrNull()?.mediaUrl ?: "",
                // 첫 번째 미디어의 URL을 사용
            )
        }
    }

    override fun loadNewerPosts(
        userId: Long,
        cursor: Long?,
        limit: Int,
    ): List<PostDetail> {
        val pageable = PageRequest.of(0, limit)
        val posts = postRepository.findNewerPostsByUserIdAndCursor(userId, cursor, pageable)
        return posts.map { post ->
            val isLiked = postLikeRepository.findByPostIdAndUserId(post.id, userId) != null
            val isSaved = postSaveRepository.findByPostIdAndUserId(post.id, userId) != null
            PostMapper.toPostDetailDTO(post, isLiked, isSaved)
        }
    }

    override fun loadOlderPosts(
        userId: Long,
        cursor: Long?,
        limit: Int,
    ): List<PostDetail> {
        val pageable = PageRequest.of(0, limit)
        val posts = postRepository.findOlderPostsByUserIdAndCursor(userId, cursor, pageable)
        return posts.map { post ->
            val isLiked = postLikeRepository.findByPostIdAndUserId(post.id, userId) != null
            val isSaved = postSaveRepository.findByPostIdAndUserId(post.id, userId) != null
            PostMapper.toPostDetailDTO(post, isLiked, isSaved)
        }
    }
}
