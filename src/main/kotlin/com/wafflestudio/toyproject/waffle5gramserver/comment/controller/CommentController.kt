package com.wafflestudio.toyproject.waffle5gramserver.comment.controller

import com.wafflestudio.toyproject.waffle5gramserver.comment.service.Comment
import com.wafflestudio.toyproject.waffle5gramserver.comment.service.CommentAlreadyLikedException
import com.wafflestudio.toyproject.waffle5gramserver.comment.service.CommentException
import com.wafflestudio.toyproject.waffle5gramserver.comment.service.CommentLikeService
import com.wafflestudio.toyproject.waffle5gramserver.comment.service.CommentNotFoundException
import com.wafflestudio.toyproject.waffle5gramserver.comment.service.CommentNotLikedException
import com.wafflestudio.toyproject.waffle5gramserver.comment.service.CommentService
import com.wafflestudio.toyproject.waffle5gramserver.comment.service.UserNotFoundException
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
class CommentController(
    private val commentService: CommentService,
    private val commentLikeService: CommentLikeService,
) {
    @GetMapping("/posts/{postId}/comments")
    fun getCommentsByPost(
        @PathVariable("postId") postId: Long,
        pageable: Pageable,
        @AuthenticationPrincipal user: InstagramUser,
    ): ResponseEntity<Page<Comment>> {
        val comments = commentService.getComments(postId, pageable, user.id)
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

    @PostMapping("/comments/{commentId}/likes")
    fun createCommentLike(
        @AuthenticationPrincipal user: InstagramUser,
        @PathVariable commentId: Long,
    ): ResponseEntity<Unit> {
        commentLikeService.create(commentId, user.id)
        return ResponseEntity.status(HttpStatus.CREATED).build()
    }

    @DeleteMapping("/comments/{commentId}/likes")
    fun deleteCommentLike(
        @AuthenticationPrincipal user: InstagramUser,
        @PathVariable commentId: Long,
    ): ResponseEntity<Unit> {
        commentLikeService.delete(commentId, user.id)
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
