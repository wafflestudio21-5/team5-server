package com.wafflestudio.toyproject.waffle5gramserver.comment.service

import com.wafflestudio.toyproject.waffle5gramserver.comment.repository.CommentLikeEntity
import com.wafflestudio.toyproject.waffle5gramserver.comment.repository.CommentLikeRepository
import com.wafflestudio.toyproject.waffle5gramserver.comment.repository.CommentRepository
import com.wafflestudio.toyproject.waffle5gramserver.user.repository.UserRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class CommentLikeServiceImpl(
    private val commentLikeRepository: CommentLikeRepository,
    private val commentRepository: CommentRepository,
    private val userRepository: UserRepository,
) : CommentLikeService {
    override fun exists(
        commentId: Long,
        userId: Long,
    ): Boolean {
        return commentLikeRepository.findByCommentIdAndUserId(commentId, userId) != null
    }

    @Transactional
    override fun create(
        commentId: Long,
        userId: Long,
    ) {
        val comment = commentRepository.findById(commentId).orElseThrow { CommentNotFoundException() }
        if (userRepository.findById(userId).isEmpty) throw UserNotFoundException()

        if (exists(commentId, userId)) throw CommentAlreadyLikedException()

        comment.incrementLikeCount()
        commentRepository.save(comment)

        commentLikeRepository.save(
            CommentLikeEntity(
                commentId = commentId,
                userId = userId,
            ),
        )
    }

    @Transactional
    override fun delete(
        commentId: Long,
        userId: Long,
    ) {
        val comment = commentRepository.findById(commentId).orElseThrow { CommentNotFoundException() }
        val like = commentLikeRepository.findByCommentIdAndUserId(commentId, userId) ?: throw CommentNotLikedException()

        comment.decrementLikeCount()
        commentRepository.save(comment)

        commentLikeRepository.delete(like)
    }
}
