package com.wafflestudio.toyproject.waffle5gramserver.reply.service

import com.wafflestudio.toyproject.waffle5gramserver.reply.repository.ReplyLikeEntity
import com.wafflestudio.toyproject.waffle5gramserver.reply.repository.ReplyLikeRepository
import com.wafflestudio.toyproject.waffle5gramserver.reply.repository.ReplyRepository
import com.wafflestudio.toyproject.waffle5gramserver.user.repository.UserRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class ReplyLikeServiceImpl(
    private val replyLikeRepository: ReplyLikeRepository,
    private val replyRepository: ReplyRepository,
    private val userRepository: UserRepository,
) : ReplyLikeService {
    override fun exists(
        replyId: Long,
        userId: Long,
    ): Boolean {
        return replyLikeRepository.findByReplyIdAndUserId(replyId, userId) != null
    }

    @Transactional
    override fun create(
        replyId: Long,
        userId: Long,
    ) {
        val reply = replyRepository.findById(replyId).orElseThrow { ReplyNotFoundException() }
        if (userRepository.findById(userId).isEmpty) throw UserNotFoundException()

        if (exists(replyId, userId)) throw ReplyAlreadyLikedException()

        reply.incrementLikeCount()
        replyRepository.save(reply)

        replyLikeRepository.save(
            ReplyLikeEntity(
                replyId = replyId,
                userId = userId,
            ),
        )
    }

    @Transactional
    override fun delete(
        replyId: Long,
        userId: Long,
    ) {
        val reply = replyRepository.findById(replyId).orElseThrow { ReplyNotFoundException() }
        val like = replyLikeRepository.findByReplyIdAndUserId(replyId, userId) ?: throw ReplyNotLikedException()

        reply.decrementLikeCount()
        replyRepository.save(reply)

        replyLikeRepository.delete(like)
    }
}
