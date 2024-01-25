package com.wafflestudio.toyproject.waffle5gramserver.feed.service

import com.wafflestudio.toyproject.waffle5gramserver.post.mapper.PostMapper
import com.wafflestudio.toyproject.waffle5gramserver.post.repository.PostLikeRepository
import com.wafflestudio.toyproject.waffle5gramserver.post.repository.PostRepository
import com.wafflestudio.toyproject.waffle5gramserver.post.repository.PostSaveRepository
import com.wafflestudio.toyproject.waffle5gramserver.post.service.PostDetail
import com.wafflestudio.toyproject.waffle5gramserver.user.repository.UserRepository
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service

@Service
class UserFeedServiceImpl(
    private val userRepository: UserRepository,
    private val postRepository: PostRepository,
    private val postLikeRepository: PostLikeRepository,
    private val postSaveRepository: PostSaveRepository,
) : UserFeedService {
    override fun getUserFeedPreview(
        username: String,
        cursor: Long?,
        limit: Int,
    ): List<PostPreview> {
        val user = userRepository.findByUsername(username).orElseThrow { throw IllegalArgumentException("User not found") }

        val pageable = PageRequest.of(0, limit)
        val posts =
            if (cursor == null) {
                postRepository.findLatestPostsByUserId(user.id, pageable)
            } else {
                postRepository.findPostsByUserIdAndCursor(user.id, cursor, pageable)
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
        username: String,
        cursor: Long?,
        limit: Int,
    ): List<PostDetail> {
        val user = userRepository.findByUsername(username).orElseThrow { throw IllegalArgumentException("User not found") }
        val pageable = PageRequest.of(0, limit)
        val posts = postRepository.findNewerPostsByUserIdAndCursor(user.id, cursor, pageable)
        return posts.map { post ->
            val isLiked = postLikeRepository.findByPostIdAndUserId(post.id, user.id) != null
            val isSaved = postSaveRepository.findByPostIdAndUserId(post.id, user.id) != null
            PostMapper.toPostDetailDTO(post, isLiked, isSaved)
        }
    }

    override fun loadOlderPosts(
        username: String,
        cursor: Long?,
        limit: Int,
    ): List<PostDetail> {
        val user = userRepository.findByUsername(username).orElseThrow { throw IllegalArgumentException("User not found") }
        val pageable = PageRequest.of(0, limit)
        val posts = postRepository.findOlderPostsByUserIdAndCursor(user.id, cursor, pageable)
        return posts.map { post ->
            val isLiked = postLikeRepository.findByPostIdAndUserId(post.id, user.id) != null
            val isSaved = postSaveRepository.findByPostIdAndUserId(post.id, user.id) != null
            PostMapper.toPostDetailDTO(post, isLiked, isSaved)
        }
    }
}
