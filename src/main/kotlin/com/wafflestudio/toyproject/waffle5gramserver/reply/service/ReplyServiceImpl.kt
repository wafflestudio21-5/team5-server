package com.wafflestudio.toyproject.waffle5gramserver.reply.service;

import com.wafflestudio.toyproject.waffle5gramserver.comment.repository.CommentRepository
import com.wafflestudio.toyproject.waffle5gramserver.reply.repository.ReplyEntity
import com.wafflestudio.toyproject.waffle5gramserver.reply.repository.ReplyRepository;
import com.wafflestudio.toyproject.waffle5gramserver.user.repository.UserEntity
import org.springframework.stereotype.Service;

@Service
class ReplyServiceImpl(
        private val replyRepository: ReplyRepository,
        private val commentRepository: CommentRepository,
) : ReplyService {

        override fun getReplies(commentId: Long, page: Long, limit: Long): List<Reply> {
                TODO()
        }

        override fun createReply(commentId: Long, content: String): ReplyBrief {
                val parentComment = commentRepository.findById(commentId)
                        .orElseThrow { throw IllegalArgumentException("Comment not found") }

                // val author = userRepository.findById(userId)

                val created = replyRepository.save(
                        ReplyEntity(
                                content = content,
                                comment = parentComment,
                                author = UserEntity(
                                        nickname = "nickname",
                                )
                        )
                )

                return ReplyBrief(created)
        }

        override fun updateReply(replyId: Long, content: String): ReplyBrief {
                val reply = replyRepository.findById(replyId)
                        .orElseThrow { throw IllegalArgumentException("Reply not found") }

                reply.updateContent(content)

                return ReplyBrief(replyRepository.save(reply))
        }

        override fun deleteReply(replyId: Long) {
                val reply = replyRepository.findById(replyId)
                        .orElseThrow { throw IllegalArgumentException("Reply not found") }

                replyRepository.delete(reply)
        }
}

private fun ReplyBrief(replyEntity: ReplyEntity) = ReplyBrief(
        replyId = replyEntity.id,
        content = replyEntity.content,
)

