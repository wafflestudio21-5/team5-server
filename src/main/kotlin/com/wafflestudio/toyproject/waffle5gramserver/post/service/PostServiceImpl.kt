package com.wafflestudio.toyproject.waffle5gramserver.post.service

import com.wafflestudio.toyproject.waffle5gramserver.post.mapper.PostMapper
import com.wafflestudio.toyproject.waffle5gramserver.post.repository.MediaType
import com.wafflestudio.toyproject.waffle5gramserver.post.repository.PostEntity
import com.wafflestudio.toyproject.waffle5gramserver.post.repository.PostLikeRepository
import com.wafflestudio.toyproject.waffle5gramserver.post.repository.PostMediaEntity
import com.wafflestudio.toyproject.waffle5gramserver.post.repository.PostMediaRepository
import com.wafflestudio.toyproject.waffle5gramserver.post.repository.PostRepository
import com.wafflestudio.toyproject.waffle5gramserver.post.repository.PostSaveRepository
import com.wafflestudio.toyproject.waffle5gramserver.user.repository.UserRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class PostServiceImpl(
    private val postRepository: PostRepository,
    private val userRepository: UserRepository,
    private val postLikeRepository: PostLikeRepository,
    private val postMediaRepository: PostMediaRepository,
    private val postImageService: PostImageService,
    private val postSaveRepository: PostSaveRepository,
) : PostService {
    override fun get(
        postId: Long,
        userId: Long,
    ): PostDetail {
        val post = postRepository.findById(postId).orElseThrow { PostNotFoundException() }
        val isLiked = postLikeRepository.findByPostIdAndUserId(postId, userId) != null
        val isSaved = postSaveRepository.findByPostIdAndUserId(postId, userId) != null
        return PostMapper.toPostDetailDTO(post, isLiked, isSaved)
    }

    @Transactional
    override fun create(
        content: String,
        fileUrls: List<String>,
        disableComment: Boolean,
        hideLike: Boolean,
        userId: Long,
    ): PostBrief {
        val user = userRepository.findById(userId).orElseThrow { UserNotFoundException() }

        val post =
            PostEntity(
                content = content,
                commentDisplayed = disableComment,
                likeCountDisplayed = hideLike,
                user = user,
            )

        fileUrls.forEachIndexed { index, url ->
            val postMedia =
                PostMediaEntity(
                    mediaUrl = url,
                    mediaOrder = index,
                    mediaType = MediaType.IMAGE,
                    post = post,
                )
            postMediaRepository.save(postMedia)
            post.addMedia(postMedia)
        }

        val entity = postRepository.save(post)
        return PostMapper.toPostBriefDTO(entity)
    }

    @Transactional
    override fun patch(
        postId: Long,
        content: String,
        userId: Long,
    ): PostBrief {
        val post = postRepository.findById(postId).orElseThrow { PostNotFoundException() }
        if (post.user.id != userId) {
            throw PostNotAuthorizedException()
        }
        post.content = content
        val entity = postRepository.save(post)
        return PostMapper.toPostBriefDTO(entity)
    }

    @Transactional
    override fun delete(
        postId: Long,
        userId: Long,
    ) {
        val post = postRepository.findById(postId).orElseThrow { PostNotFoundException() }
        if (post.user.id != userId) {
            throw PostNotAuthorizedException()
        }

        val postMediaUrls = postMediaRepository.findAllByPostId(postId)
        postImageService.deleteImages(postMediaUrls)

        postLikeRepository.deleteAllByPostId(postId)
        postSaveRepository.deleteAllByPostId(postId)
        postRepository.delete(post)
    }
}
