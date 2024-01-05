package com.wafflestudio.toyproject.waffle5gramserver.comment.service

import com.wafflestudio.toyproject.waffle5gramserver.comment.repository.CommentRepository
import org.springframework.stereotype.Service

@Service
class CommentServiceImpl(
    private val commentRepository: CommentRepository,
) : CommentService {
    override fun getComments() {
        TODO("Not yet implemented")
    }

    override fun createComment(postId: Long, content: String): CommentBrief {
        TODO("Not yet implemented")
    }

    override fun updateComment(commentId: Long, content: String): CommentBrief {
        TODO("Not yet implemented")
    }

    override fun deleteComment(commentId: Long) {
        TODO("Not yet implemented")
    }
}