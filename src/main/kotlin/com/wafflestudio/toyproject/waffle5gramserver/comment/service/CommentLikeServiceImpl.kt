package com.wafflestudio.toyproject.waffle5gramserver.comment.service

import com.wafflestudio.toyproject.waffle5gramserver.comment.repository.CommentLikeEntity
import com.wafflestudio.toyproject.waffle5gramserver.comment.repository.CommentLikeRepository
import com.wafflestudio.toyproject.waffle5gramserver.comment.repository.CommentRepository
import com.wafflestudio.toyproject.waffle5gramserver.user.repository.UserRepository
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

    override fun create(
        commentId: Long,
        userId: Long,
    ) {
        if (commentRepository.findById(commentId).isEmpty) throw CommentNotFoundException()
        if (userRepository.findById(userId).isEmpty) throw UserNotFoundException()
        if (exists(commentId, userId)) throw CommentAlreadyLikedException()
        commentLikeRepository.save(
            CommentLikeEntity(
                commentId = commentId,
                userId = userId,
            ),
        )
    }

    override fun delete(
        commentId: Long,
        userId: Long,
    ) {
        val like = commentLikeRepository.findByCommentIdAndUserId(commentId, userId) ?: throw CommentNotLikedException()
        commentLikeRepository.delete(like)
    }
}
