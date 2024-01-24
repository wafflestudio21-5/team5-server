package com.wafflestudio.toyproject.waffle5gramserver.post.service

import com.wafflestudio.toyproject.waffle5gramserver.post.repository.PostLikeEntity
import com.wafflestudio.toyproject.waffle5gramserver.post.repository.PostLikeRepository
import com.wafflestudio.toyproject.waffle5gramserver.post.repository.PostRepository
import com.wafflestudio.toyproject.waffle5gramserver.user.repository.UserRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class PostLikeServiceImpl(
    private val postLikeRepository: PostLikeRepository,
    private val postRepository: PostRepository,
    private val userRepository: UserRepository,
) : PostLikeService {
    override fun exists(
        postId: Long,
        userId: Long,
    ): Boolean {
        return postLikeRepository.findByPostIdAndUserId(postId, userId) != null
    }

    @Transactional
    override fun create(
        postId: Long,
        userId: Long,
    ) {
        val post = postRepository.findById(postId).orElseThrow { PostNotFoundException() }
        if (userRepository.findById(userId).isEmpty) throw UserNotFoundException()

        if (exists(postId, userId)) throw PostAlreadyLikedException()

        post.incrementLikeCount()
        postRepository.save(post)

        postLikeRepository.save(
            PostLikeEntity(
                postId = postId,
                userId = userId,
            ),
        )
    }

    @Transactional
    override fun delete(
        postId: Long,
        userId: Long,
    ) {
        val post = postRepository.findById(postId).orElseThrow { PostNotFoundException() }
        val like = postLikeRepository.findByPostIdAndUserId(postId, userId) ?: throw PostNotLikedException()

        post.decrementLikeCount()
        postRepository.save(post)

        postLikeRepository.delete(like)
    }
}
