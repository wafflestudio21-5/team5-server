package com.wafflestudio.toyproject.waffle5gramserver.feed.service

import com.wafflestudio.toyproject.waffle5gramserver.post.mapper.PostMapper
import com.wafflestudio.toyproject.waffle5gramserver.post.repository.PostLikeRepository
import com.wafflestudio.toyproject.waffle5gramserver.post.repository.PostRepository
import com.wafflestudio.toyproject.waffle5gramserver.post.repository.PostSaveRepository
import com.wafflestudio.toyproject.waffle5gramserver.post.service.PostDetail
import com.wafflestudio.toyproject.waffle5gramserver.user.repository.UserRepository
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Slice
import org.springframework.stereotype.Service

@Service
class SavedFeedServiceImpl(
    private val userRepository: UserRepository,
    private val postRepository: PostRepository,
    private val postLikeRepository: PostLikeRepository,
    private val postSaveRepository: PostSaveRepository,
) : SavedFeedService {
    override fun getSavedFeedPreview(
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

    override fun getSavedFeed(
        userId: Long,
        pageable: Pageable,
    ): Slice<PostDetail> {
        val user = userRepository.findById(userId).orElseThrow { throw IllegalArgumentException("User not found") }
        // 저장된 게시물을 조회
        val postSaveEntities = postSaveRepository.findByUserId(user.id, pageable).content
        val postIds = postSaveEntities.map { it.postId }
        val posts = postRepository.findByIdIn(postIds, pageable)

        return posts.map { post ->
            val isLiked = postLikeRepository.findByPostIdAndUserId(post.id, user.id) != null
            val isSaved = postSaveRepository.findByPostIdAndUserId(post.id, user.id) != null
            PostMapper.toPostDetailDTO(post, isLiked, isSaved)
        }
    }
}
