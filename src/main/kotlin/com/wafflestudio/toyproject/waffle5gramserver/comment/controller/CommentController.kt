package com.wafflestudio.toyproject.waffle5gramserver.comment.controller

import com.wafflestudio.toyproject.waffle5gramserver.comment.service.CommentService
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
class CommentController(
    private val commentService: CommentService,
) {
    @GetMapping("/posts/{postId}/comments")
    fun getComments(
        @PathVariable("postId") postId: Long,
        @RequestParam(defaultValue = "1", required = false) page: Long,
        @RequestParam(defaultValue = "10", required = false) limit: Long,
    ) {
        TODO()
    }
    @PostMapping("/posts/{postId}/comments")
    fun createComment(
        @PathVariable("postId") postId: Long,
        @RequestBody content: String,
    ) {
        TODO()
    }
    @PutMapping("/comments/{commentId}")
    fun updateComment(
        @PathVariable("commentId") commentId: Long,
        @RequestBody content: String,
    ) {
        TODO()
    }
    @DeleteMapping("/comments/{commentId}")
    fun deleteComment(
        @PathVariable("commentId") commentId: Long,
    ) {
        TODO()
    }
}