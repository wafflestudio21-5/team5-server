package com.wafflestudio.toyproject.waffle5gramserver.reply.service

import com.wafflestudio.toyproject.waffle5gramserver.reply.repository.ReplyLikeEntity
import com.wafflestudio.toyproject.waffle5gramserver.reply.repository.ReplyLikeRepository
import com.wafflestudio.toyproject.waffle5gramserver.reply.repository.ReplyRepository
import com.wafflestudio.toyproject.waffle5gramserver.user.repository.UserRepository

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

    override fun create(
        replyId: Long,
        userId: Long,
    ) {
        if (replyRepository.findById(replyId).isEmpty) throw ReplyNotFoundException()
        if (userRepository.findById(userId).isEmpty) throw UserNotFoundException()
        if (exists(replyId, userId)) throw ReplyAlreadyLikedException()
        replyLikeRepository.save(
            ReplyLikeEntity(
                replyId = replyId,
                userId = userId,
            ),
        )
    }

    override fun delete(
        replyId: Long,
        userId: Long,
    ) {
        val like = replyLikeRepository.findByReplyIdAndUserId(replyId, userId) ?: throw ReplyNotLikedException()
        replyLikeRepository.delete(like)
    }
}
