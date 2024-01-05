package com.wafflestudio.toyproject.waffle5gramserver.reply.controller

import com.wafflestudio.toyproject.waffle5gramserver.reply.service.Reply
import com.wafflestudio.toyproject.waffle5gramserver.reply.service.ReplyService
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1")
class ReplyController(
    private val replyService: ReplyService,
) {

    @GetMapping("/comments/{commentId}/replies")
    fun getReplies(
        @PathVariable("commentId") commentId: Long,
        @RequestParam(defaultValue = "1", required = false) page: Long,
        @RequestParam(defaultValue = "1", required = false) limit: Long,
    ): RepliesResponse {
        return RepliesResponse(replyService.getReplies(commentId, page, limit))
    }

    @PostMapping("/comments/{commentId}/replies")
    fun createReply(
        @PathVariable("commentId") commentId: Long,
        @RequestBody content: String,
        // @Authenticated user: User,
    ) {
        replyService.createReply(commentId, content)
    }

    @PutMapping("/replies/{replyId}")
    fun updateReply(
        @PathVariable("replyId") replyId: Long,
        @RequestBody content: String,
    ) {
        replyService.updateReply(replyId, content)
    }

    @DeleteMapping("api/v1/replies/{replyId}")
    fun deleteReply(
        @PathVariable("replyId") replyId: Long,
    ) {
        return replyService.deleteReply(replyId)
    }
}

data class RepliesResponse(
    val replies: List<Reply>,
)