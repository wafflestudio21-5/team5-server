package com.wafflestudio.toyproject.waffle5gramserver.comment.controller

import com.wafflestudio.toyproject.waffle5gramserver.comment.service.*
import com.wafflestudio.toyproject.waffle5gramserver.user.service.InstagramUser
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1")
class CommentController(
    private val commentService: CommentService,
) {
    @GetMapping("/posts/{postId}/comments")
    fun getCommentsByPost(
        @PathVariable("postId") postId: Long,
        pageable: Pageable,
    ): ResponseEntity<Page<Comment>> {
        val comments = commentService.getComments(postId, pageable)
        return ResponseEntity.ok(comments)
    }

    @PostMapping("/posts/{postId}/comments")
    fun createComment(
        @AuthenticationPrincipal user: InstagramUser,
        @PathVariable("postId") postId: Long,
        @RequestBody content: String,
    ): ResponseEntity<Comment> {
        val comment = commentService.create(postId, content, user.id)
        return ResponseEntity.status(HttpStatus.CREATED).body(comment)
    }

    @PutMapping("/comments/{commentId}")
    fun updateComment(
        @AuthenticationPrincipal user: InstagramUser,
        @PathVariable("commentId") commentId: Long,
        @RequestBody content: String,
    ): ResponseEntity<Comment> {
        val comment = commentService.patch(commentId, content, user.id)
        return ResponseEntity.ok(comment)
    }

    @DeleteMapping("/comments/{commentId}")
    fun deleteComment(
        @AuthenticationPrincipal user: InstagramUser,
        @PathVariable("commentId") commentId: Long,
    ): ResponseEntity<Unit> {
        commentService.delete(commentId, user.id)
        return ResponseEntity.noContent().build()
    }

    @ExceptionHandler(CommentException::class)
    fun handleException(e: CommentException): ResponseEntity<Any> {
        return when (e) {
            is CommentNotFoundException -> ResponseEntity.notFound().build()
            is CommentNotLikedException -> ResponseEntity.status(HttpStatus.FORBIDDEN).build()
            is CommentAlreadyLikedException -> ResponseEntity.status(HttpStatus.CONFLICT).build()
            is UserNotFoundException -> ResponseEntity.notFound().build()
            else -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build()
        }
    }
}
