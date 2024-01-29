package com.wafflestudio.toyproject.waffle5gramserver.post.service

import com.wafflestudio.toyproject.waffle5gramserver.post.repository.PostRepository
import com.wafflestudio.toyproject.waffle5gramserver.post.repository.PostSaveEntity
import com.wafflestudio.toyproject.waffle5gramserver.post.repository.PostSaveRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class PostSaveServiceImpl(
    private val postSaveRepository: PostSaveRepository,
    private val postRepository: PostRepository,
) : PostSaveService {
    override fun exists(
        postId: Long,
        userId: Long,
    ): Boolean {
        return postSaveRepository.findByUserIdAndPostId(userId, postId) != null
    }

    @Transactional
    override fun create(
        postId: Long,
        userId: Long,
    ) {
        val post = postRepository.findById(postId).orElseThrow { PostNotFoundException() }

        if (exists(postId, userId)) throw PostAlreadySavedException()

        postSaveRepository.save(
            PostSaveEntity(
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
        val existingSave = postSaveRepository.findByUserIdAndPostId(userId, postId) ?: throw PostNotSavedException()

        postSaveRepository.delete(existingSave)
    }
}
