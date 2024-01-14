package com.wafflestudio.toyproject.waffle5gramserver.post.controller

import com.wafflestudio.toyproject.waffle5gramserver.post.service.PostBrief
import com.wafflestudio.toyproject.waffle5gramserver.post.service.PostDetail
import com.wafflestudio.toyproject.waffle5gramserver.post.service.PostService
import com.wafflestudio.toyproject.waffle5gramserver.user.service.InstagramUser
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class PostController(private val postService: PostService) {
    @PostMapping("/posts")
    fun createPost(
        @AuthenticationPrincipal user: InstagramUser,
        @RequestParam("content") content: String,
        @RequestParam("hideComments", defaultValue = "false") hideComments: Boolean,
        @RequestParam("hideLikes", defaultValue = "false") hideLikes: Boolean,
        @RequestParam("files") files: List<String>,
    ): ResponseEntity<Any> {
        // 게시물 생성 로직 처리
        val post = postService.create(content = content, fileUrls = files, disableComment = hideComments, hideLike = hideLikes, userId = user.id)
        return ResponseEntity.ok(post)
    }

    @GetMapping("/posts/{postId}")
    fun getPost(@AuthenticationPrincipal user: InstagramUser, @PathVariable postId: Long): PostDetail {
        return postService.get(postId = postId, userId = user.id)
    }

    @DeleteMapping("/posts/{postId}")
    fun deletePost(@AuthenticationPrincipal user: InstagramUser, @PathVariable postId: Long) {
        return postService.delete(postId = postId, userId = user.id)
    }

    @PutMapping("/posts/{postId}")
    fun updatePost(@AuthenticationPrincipal user: InstagramUser, @PathVariable postId: Long, @RequestParam content: String): PostBrief {
        return postService.patch(postId = postId, content = content, userId = user.id)
    }
}
