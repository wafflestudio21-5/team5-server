package com.wafflestudio.toyproject.waffle5gramserver.comment.service

import com.wafflestudio.toyproject.waffle5gramserver.comment.repository.CommentEntity
import com.wafflestudio.toyproject.waffle5gramserver.comment.repository.CommentLikeRepository
import com.wafflestudio.toyproject.waffle5gramserver.comment.repository.CommentRepository
import com.wafflestudio.toyproject.waffle5gramserver.post.repository.PostRepository
import com.wafflestudio.toyproject.waffle5gramserver.post.service.PostNotFoundException
import com.wafflestudio.toyproject.waffle5gramserver.post.service.UserNotFoundException
import com.wafflestudio.toyproject.waffle5gramserver.reply.repository.ReplyRepository
import com.wafflestudio.toyproject.waffle5gramserver.user.repository.UserRepository
import jakarta.transaction.Transactional
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class CommentServiceImpl(
    private val commentRepository: CommentRepository,
    private val userRepository: UserRepository,
    private val postRepository: PostRepository,
    private val commentLikeRepository: CommentLikeRepository,
    private val replyRepository: ReplyRepository,
) : CommentService {
    override fun getComments(
        postId: Long,
        pageable: Pageable,
        userId: Long,
    ): Page<Comment> {
        val comments = commentRepository.findByPostId(postId, pageable)

        return comments.map {
            convertToDTO(it, userId)
        }
    }

    @Transactional
    override fun create(
        postId: Long,
        content: String,
        userId: Long,
    ): Comment {
        val user = userRepository.findById(userId).orElseThrow { UserNotFoundException() }

        val post = postRepository.findById(postId).orElseThrow { PostNotFoundException() }

        val comment =
            CommentEntity(
                text = content,
                user = user,
                post = post,
                createdAt = LocalDateTime.now(),
            )

        val entity = commentRepository.save(comment)
        return convertToDTO(entity, userId)
    }

    @Transactional
    override fun patch(
        commentId: Long,
        content: String,
        userId: Long,
    ): Comment {
        val comment = commentRepository.findById(commentId).orElseThrow { CommentNotFoundException() }

        if (comment.user.id != userId) {
            throw CommentNotAuthorizedException()
        }

        comment.text = content
        val entity = commentRepository.save(comment)
        return convertToDTO(entity, userId)
    }

    @Transactional
    override fun delete(
        commentId: Long,
        userId: Long,
    ) {
        val comment = commentRepository.findById(commentId).orElseThrow { CommentNotFoundException() }
        if (comment.user.id != userId && comment.post.user.id != userId) {
            throw CommentNotAuthorizedException()
        }
        commentRepository.delete(comment)
    }

    fun convertToDTO(
        comment: CommentEntity,
        userId: Long,
    ): Comment {
        val likeCount = commentLikeRepository.countByCommentId(comment.id)
        val replyCount = replyRepository.countByCommentId(comment.id)
        val isLiked = commentLikeRepository.findByCommentIdAndUserId(comment.id, userId) != null
        return Comment(
            id = comment.id,
            postId = comment.post.id,
            userId = comment.user.id,
            username = comment.user.username,
            userProfileImageUrl = comment.user.profileImageUrl,
            text = comment.text,
            createdAt = comment.createdAt,
            likeCount = likeCount,
            replyCount = replyCount,
            liked = isLiked,
        )
    }
}
