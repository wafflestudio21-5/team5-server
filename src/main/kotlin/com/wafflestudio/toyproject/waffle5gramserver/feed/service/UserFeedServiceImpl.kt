package com.wafflestudio.toyproject.waffle5gramserver.feed.service

import com.wafflestudio.toyproject.waffle5gramserver.follow.exception.PrivateException
import com.wafflestudio.toyproject.waffle5gramserver.follow.repository.FollowRepository
import com.wafflestudio.toyproject.waffle5gramserver.global.error_handling.ErrorCode
import com.wafflestudio.toyproject.waffle5gramserver.post.mapper.PostMapper
import com.wafflestudio.toyproject.waffle5gramserver.post.repository.PostLikeRepository
import com.wafflestudio.toyproject.waffle5gramserver.post.repository.PostRepository
import com.wafflestudio.toyproject.waffle5gramserver.post.repository.PostSaveRepository
import com.wafflestudio.toyproject.waffle5gramserver.post.service.PostDetail
import com.wafflestudio.toyproject.waffle5gramserver.user.repository.UserRepository
import com.wafflestudio.toyproject.waffle5gramserver.user.service.InstagramUser
import jakarta.transaction.Transactional
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Slice
import org.springframework.stereotype.Service

@Service
class UserFeedServiceImpl(
    private val userRepository: UserRepository,
    private val postRepository: PostRepository,
    private val followRepository: FollowRepository,
    private val postLikeRepository: PostLikeRepository,
    private val postSaveRepository: PostSaveRepository,
) : UserFeedService {
    override fun getUserFeedPreview(
        authuser: InstagramUser,
        username: String,
        pageable: Pageable,
    ): Slice<PostPreview> {
        val user = userRepository.findByUsername(username).orElseThrow { throw IllegalArgumentException("User not found") }
        if (user.isPrivate && (followRepository.findByFollowerUserIdAndFolloweeUserId(authuser.id, user.id) == null)) {
            throw PrivateException(ErrorCode.USER_PRIVATE_NOT_FOLLOWING)
        }
        val posts = postRepository.findPostsByUserId(user.id, pageable)

        return posts.map { post ->
            PostPreview(
                id = post.id,
                thumbnailUrl = post.medias.firstOrNull()?.mediaUrl ?: "",
                // 첫 번째 미디어의 URL을 사용
            )
        }
    }

    @Transactional
    override fun getUserFeed(
        authuser: InstagramUser,
        username: String,
        pageable: Pageable,
    ): Slice<PostDetail> {
        val user = userRepository.findByUsername(username).orElseThrow { throw IllegalArgumentException("User not found") }
        if (user.isPrivate && (followRepository.findByFollowerUserIdAndFolloweeUserId(authuser.id, user.id) == null)) {
            throw PrivateException(ErrorCode.USER_PRIVATE_NOT_FOLLOWING)
        }
        val posts = postRepository.findPostsByUserId(user.id, pageable)
        return posts.map { post ->
            val isLiked = postLikeRepository.findByPostIdAndUserId(post.id, user.id) != null
            val isSaved = postSaveRepository.findByPostIdAndUserId(post.id, user.id) != null
            PostMapper.toPostDetailDTO(post, isLiked, isSaved)
        }
    }
}
