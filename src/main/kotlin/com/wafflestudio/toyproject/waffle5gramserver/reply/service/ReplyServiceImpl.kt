package com.wafflestudio.toyproject.waffle5gramserver.reply.service

import com.wafflestudio.toyproject.waffle5gramserver.comment.repository.CommentRepository
import com.wafflestudio.toyproject.waffle5gramserver.reply.repository.ReplyEntity
import com.wafflestudio.toyproject.waffle5gramserver.reply.repository.ReplyLikeRepository
import com.wafflestudio.toyproject.waffle5gramserver.reply.repository.ReplyRepository
import com.wafflestudio.toyproject.waffle5gramserver.user.repository.UserRepository
import jakarta.transaction.Transactional
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class ReplyServiceImpl(
    private val replyRepository: ReplyRepository,
    private val commentRepository: CommentRepository,
    private val userRepository: UserRepository,
    private val replyLikeRepository: ReplyLikeRepository,
) : ReplyService {
    override fun getReplies(
        commentId: Long,
        pageable: Pageable,
    ): Page<Reply> {
        val replies = replyRepository.findByCommentId(commentId, pageable)
        return replies.map { convertToDTO(it) }
    }

    @Transactional
    override fun create(
        commentId: Long,
        content: String,
        userId: Long,
    ): Reply {
        val author = userRepository.findById(userId).orElseThrow { throw IllegalArgumentException("User not found") }
        val comment = commentRepository.findById(commentId).orElseThrow { throw IllegalArgumentException("Comment not found") }

        val reply =
            ReplyEntity(
                content = content,
                user = author,
                comment = comment,
                createdAt = LocalDateTime.now(),
            )

        val entity = replyRepository.save(reply)
        return convertToDTO(entity)
    }

    @Transactional
    override fun patch(
        replyId: Long,
        content: String,
        userId: Long,
    ): Reply {
        val reply = replyRepository.findById(replyId).orElseThrow { throw IllegalArgumentException("Reply not found") }

        if (reply.user.id != userId) {
            throw ReplyNotAuthorizedException()
        }

        reply.content = content
        val entity = replyRepository.save(reply)
        return convertToDTO(entity)
    }

    @Transactional
    override fun delete(
        replyId: Long,
        userId: Long,
    ) {
        val reply = replyRepository.findById(replyId).orElseThrow { ReplyNotFoundException() }
        if (reply.user.id != userId) {
            throw ReplyNotAuthorizedException()
        }
        replyRepository.delete(reply)
    }

    fun convertToDTO(reply: ReplyEntity): Reply {
        val likeCount = replyLikeRepository.countByReplyId(reply.id)
        return Reply(
            id = reply.id,
            commentId = reply.comment.id,
            userId = reply.user.id,
            username = reply.user.username,
            userProfileImageUrl = reply.user.profileImageUrl,
            content = reply.content,
            createdAt = reply.createdAt,
            likeCount = likeCount,
        )
    }
}
