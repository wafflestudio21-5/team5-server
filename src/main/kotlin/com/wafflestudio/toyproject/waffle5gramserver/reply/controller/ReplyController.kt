package com.wafflestudio.toyproject.waffle5gramserver.reply.controller

import com.wafflestudio.toyproject.waffle5gramserver.reply.service.Reply
import com.wafflestudio.toyproject.waffle5gramserver.reply.service.ReplyService
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class ReplyController(
    private val replyService: ReplyService,
) {

    @GetMapping("api/v1/comments/{commentId}/replies")
    fun getReplies(
        @PathVariable commentId: Long,
        @RequestParam page: Long,
        @RequestParam limit: Long,
    ): RepliesResponse {
        return RepliesResponse(replyService.getReplies(commentId, page, limit))
    }

    @PostMapping("api/v1/comments/{commentId}/replies")
    fun createReply(
        @PathVariable commentId: Long,
        @RequestParam content: String,
        // @Authenticated user: User,
    ) {
        replyService.createReply(commentId, content)
    }

    @PutMapping("api/v1/replies/{replyId}")
    fun updateReply(
        @PathVariable replyId: Long,
        @RequestParam content: String,
    ) {
        replyService.updateReply(replyId, content)
    }

    @DeleteMapping("api/v1/replies/{replyId}")
    fun deleteReply(
        @PathVariable replyId: Long,
    ) {
        return replyService.deleteReply(replyId)
    }
}

data class RepliesResponse(
    val replies: List<Reply>,
)