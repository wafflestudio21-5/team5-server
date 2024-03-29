package com.wafflestudio.toyproject.waffle5gramserver.feed.service

import com.wafflestudio.toyproject.waffle5gramserver.post.mapper.PostMapper
import com.wafflestudio.toyproject.waffle5gramserver.post.repository.PostLikeRepository
import com.wafflestudio.toyproject.waffle5gramserver.post.repository.PostRepository
import com.wafflestudio.toyproject.waffle5gramserver.post.repository.PostSaveRepository
import com.wafflestudio.toyproject.waffle5gramserver.post.service.PostDetail
import com.wafflestudio.toyproject.waffle5gramserver.user.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class SavedFeedServiceImpl(
    private val userRepository: UserRepository,
    private val postRepository: PostRepository,
    private val postLikeRepository: PostLikeRepository,
    private val postSaveRepository: PostSaveRepository,
) : SavedFeedService {
    override fun getSavedFeedPreview(userId: Long): List<PostPreview> {
        val user = userRepository.findById(userId).orElseThrow { throw IllegalArgumentException("User not found") }
        // 저장된 게시물을 조회
        val postSaveEntities = postSaveRepository.findByUserId(user.id)
        val postIds = postSaveEntities.map { it.postId }
        val posts = postRepository.findByIdIn(postIds)

        return posts.map { post ->
            PostPreview(
                id = post.id,
                thumbnailUrl = post.medias.firstOrNull()?.mediaUrl ?: "",
                // 첫 번째 미디어의 URL을 사용
            )
        }
    }

    override fun getSavedFeed(userId: Long): List<PostDetail> {
        val user = userRepository.findById(userId).orElseThrow { throw IllegalArgumentException("User not found") }
        // 저장된 게시물을 조회
        val postSaveEntities = postSaveRepository.findByUserId(user.id)
        val postIds = postSaveEntities.map { it.postId }
        val posts = postRepository.findByIdIn(postIds)

        return posts.map { post ->
            val isLiked = postLikeRepository.findByPostIdAndUserId(post.id, user.id) != null
            val isSaved = postSaveRepository.findByPostIdAndUserId(post.id, user.id) != null
            PostMapper.toPostDetailDTO(post, isLiked, isSaved)
        }
    }
}
