package com.wafflestudio.toyproject.waffle5gramserver.post.controller

import com.wafflestudio.toyproject.waffle5gramserver.post.repository.PostEntity
import com.wafflestudio.toyproject.waffle5gramserver.post.service.PostService
import org.springframework.boot.autoconfigure.security.SecurityProperties.User
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
class PostController (private val postService: PostService)
{
    @PostMapping("/posts")
    fun createPost(@AuthenticationPrincipal user: User,
                   @RequestParam("content") content: String,
                   @RequestParam("hideComments", defaultValue = "false") hideComments: Boolean,
                   @RequestParam("hideLikes", defaultValue = "false") hideLikes: Boolean,
                   @RequestParam("files") files: List<MultipartFile>,
                   ): ResponseEntity<Any> {
        // 게시물 생성 로직 처리
        val post = postService.create(content = content, files = files, disableComment = hideComments, hideLike =  hideLikes, userId = user.id)
        return ResponseEntity.ok(post)
    }

    @GetMapping("/posts/{postId}")
    fun getPost(@AuthenticationPrincipal user: User, @PathVariable postId: Long) : PostEntity {
        return postService.get(postId = postId, userId = user.id)
    }

    @DeleteMapping("/posts/{postId}")
    fun deletePost(@AuthenticationPrincipal user: User, @PathVariable postId: Long) : Boolean {
        return postService.delete(postId = postId, userId = user.id)
    }

    @PutMapping("/posts/{postId}")
    fun updatePost(@AuthenticationPrincipal user: User, @PathVariable postId: Long, @RequestParam content: String) : PostEntity {
        return postService.patch(postId = postId, content =  content, userId = user.id)
    }
}

