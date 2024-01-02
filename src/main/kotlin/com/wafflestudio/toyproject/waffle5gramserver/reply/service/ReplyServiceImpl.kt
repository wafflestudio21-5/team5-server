package com.wafflestudio.toyproject.waffle5gramserver.reply.service;

import com.wafflestudio.toyproject.waffle5gramserver.reply.repository.ReplyRepository;
import org.springframework.stereotype.Service;

@Service
class ReplyServiceImpl(
        private val replyRepository: ReplyRepository,
) : ReplyService {

        override fun getReply(page: Long, limit: Long): List<Reply> {
                TODO("Not yet implemented")
        }

        override fun createReply(content: String): Reply {
                TODO("Not yet implemented")
        }

        override fun updateReply(replyId: Long, content: String): Reply {
                TODO("Not yet implemented")
        }

        override fun deleteReply(replyId: Long) {
                TODO("Not yet implemented")
        }
}

