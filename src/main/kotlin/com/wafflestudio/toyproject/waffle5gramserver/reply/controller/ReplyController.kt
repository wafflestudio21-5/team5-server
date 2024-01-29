package com.wafflestudio.toyproject.waffle5gramserver.reply.controller

import com.wafflestudio.toyproject.waffle5gramserver.reply.service.Reply
import com.wafflestudio.toyproject.waffle5gramserver.reply.service.ReplyAlreadyLikedException
import com.wafflestudio.toyproject.waffle5gramserver.reply.service.ReplyException
import com.wafflestudio.toyproject.waffle5gramserver.reply.service.ReplyLikeService
import com.wafflestudio.toyproject.waffle5gramserver.reply.service.ReplyNotAuthorizedException
import com.wafflestudio.toyproject.waffle5gramserver.reply.service.ReplyNotFoundException
import com.wafflestudio.toyproject.waffle5gramserver.reply.service.ReplyNotLikedException
import com.wafflestudio.toyproject.waffle5gramserver.reply.service.ReplyService
import com.wafflestudio.toyproject.waffle5gramserver.reply.service.UserNotFoundException
import com.wafflestudio.toyproject.waffle5gramserver.user.service.InstagramUser
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1")
class ReplyController(
    private val replyService: ReplyService,
    private val replyLikeService: ReplyLikeService,
) {
    @GetMapping("/comments/{commentId}/replies")
    fun getReplies(
        @PathVariable("commentId") commentId: Long,
        pageable: Pageable,
        @AuthenticationPrincipal user: InstagramUser,
    ): ResponseEntity<Page<Reply>> {
        val replies = replyService.getReplies(commentId, pageable, userId = user.id)
        return ResponseEntity.ok(replies)
    }

    @PostMapping("/comments/{commentId}/replies")
    fun createReply(
        @AuthenticationPrincipal user: InstagramUser,
        @PathVariable("commentId") commentId: Long,
        @RequestBody request: CreateReplyRequest,
    ): ResponseEntity<Reply> {
        val reply = replyService.create(commentId, request.content, user.id)
        return ResponseEntity.status(HttpStatus.CREATED).body(reply)
    }

    @PutMapping("/replies/{replyId}")
    fun updateReply(
        @AuthenticationPrincipal user: InstagramUser,
        @PathVariable("replyId") replyId: Long,
        @RequestBody request: CreateReplyRequest,
    ): ResponseEntity<Reply> {
        val reply = replyService.patch(replyId, request.content, user.id)
        return ResponseEntity.ok(reply)
    }

    @DeleteMapping("api/v1/replies/{replyId}")
    fun deleteReply(
        @AuthenticationPrincipal user: InstagramUser,
        @PathVariable("replyId") replyId: Long,
    ): ResponseEntity<Unit> {
        replyService.delete(replyId, user.id)
        return ResponseEntity.noContent().build()
    }

    @PostMapping("/replies/{replyId}/likes")
    fun createReplyLike(
        @AuthenticationPrincipal user: InstagramUser,
        @PathVariable("replyId") replyId: Long,
    ): ResponseEntity<Unit> {
        replyLikeService.create(replyId, user.id)
        return ResponseEntity.status(HttpStatus.CREATED).build()
    }

    @DeleteMapping("/replies/{replyId}/likes")
    fun unlikeReply(
        @AuthenticationPrincipal user: InstagramUser,
        @PathVariable("replyId") replyId: Long,
    ): ResponseEntity<Unit> {
        replyLikeService.delete(replyId, user.id)
        return ResponseEntity.noContent().build()
    }

    @ExceptionHandler(ReplyException::class)
    fun handleReplyException(e: ReplyException): ResponseEntity<Any> {
        return when (e) {
            is ReplyNotFoundException -> ResponseEntity.notFound().build()
            is ReplyNotAuthorizedException -> ResponseEntity.status(HttpStatus.FORBIDDEN).build()
            is ReplyAlreadyLikedException -> ResponseEntity.status(HttpStatus.CONFLICT).build()
            is ReplyNotLikedException -> ResponseEntity.status(HttpStatus.CONFLICT).build()
            is UserNotFoundException -> ResponseEntity.notFound().build()
        }
    }
}

data class CreateReplyRequest(
    val content: String,
)
