package com.wafflestudio.toyproject.waffle5gramserver.post.controller

import com.wafflestudio.toyproject.waffle5gramserver.post.service.*
import com.wafflestudio.toyproject.waffle5gramserver.user.service.InstagramUser

import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
class PostController (private val postService: PostService)
{
    @PostMapping("/posts")
    fun createPost(@AuthenticationPrincipal user: InstagramUser,
                   @RequestParam("content") content: String,
                   @RequestParam("hideComments", defaultValue = "false") hideComments: Boolean,
                   @RequestParam("hideLikes", defaultValue = "false") hideLikes: Boolean,
                   @RequestParam("files") files: List<String>,
                   ): ResponseEntity<Any> {
        // 게시물 생성 로직 처리
        val post = postService.create(content = content, fileUrls = files, disableComment = hideComments, hideLike =  hideLikes, userId = user.id)
        return ResponseEntity.ok(post)
    }

    @GetMapping("/posts/{postId}")
    fun getPost(@AuthenticationPrincipal user: InstagramUser, @PathVariable postId: Long) : PostDetail {
        return postService.get(postId = postId, userId = user.id)
    }

    @DeleteMapping("/posts/{postId}")
    fun deletePost(@AuthenticationPrincipal user: InstagramUser, @PathVariable postId: Long) : Unit {
        return postService.delete(postId = postId, userId = user.id)
    }

    @PutMapping("/posts/{postId}")
    fun updatePost(@AuthenticationPrincipal user: InstagramUser, @PathVariable postId: Long, @RequestParam content: String) : PostBrief {
        return postService.patch(postId = postId, content =  content, userId = user.id)
    }

    @ExceptionHandler(PostException::class)
    fun handleException(e: PostException): ResponseEntity<Any> {
        when (e) {
            is PostNotFoundException -> return ResponseEntity.notFound().build()
            is PostNotAuthorizedException -> return ResponseEntity.status(403).build()
            is UserNotFoundException -> return ResponseEntity.status(404).build()
            is PostAlreadyLikedException, is PostNotLikedException -> return ResponseEntity.status(409).build()
            else -> return ResponseEntity.status(500).build()
        }
    }
}