package com.wafflestudio.toyproject.waffle5gramserver.post.service

import com.wafflestudio.toyproject.waffle5gramserver.post.repository.PostSaveEntity
import com.wafflestudio.toyproject.waffle5gramserver.post.repository.PostSaveRepository
import org.springframework.stereotype.Service

@Service
class PostSaveServiceImpl(
    private val postSaveRepository: PostSaveRepository,
) : PostSaveService {
    override fun exists(
        postId: Long,
        userId: Long,
    ): Boolean {
        return postSaveRepository.findByUserIdAndPostId(userId, postId) != null
    }

    override fun create(
        postId: Long,
        userId: Long,
    ) {
        val existingSave = postSaveRepository.findByUserIdAndPostId(userId, postId)
        if (existingSave != null) {
            throw PostAlreadySavedException()
        }

        val newSave =
            PostSaveEntity(
                userId = userId,
                postId = postId,
                createdAt = System.currentTimeMillis(),
            )

        postSaveRepository.save(newSave)
    }

    override fun delete(
        postId: Long,
        userId: Long,
    ) {
        val existingSave = postSaveRepository.findByUserIdAndPostId(userId, postId) ?: throw PostNotSavedException()

        postSaveRepository.delete(existingSave)
    }
}
