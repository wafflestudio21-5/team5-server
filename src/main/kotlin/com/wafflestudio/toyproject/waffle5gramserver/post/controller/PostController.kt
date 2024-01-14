package com.wafflestudio.toyproject.waffle5gramserver.post.controller

import com.wafflestudio.toyproject.waffle5gramserver.post.service.*
import com.wafflestudio.toyproject.waffle5gramserver.user.service.InstagramUser
import org.springframework.http.HttpStatus

import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1")
class PostController (private val postService: PostService)
{
    @PostMapping("/posts")
    fun createPost(@AuthenticationPrincipal user: InstagramUser,
                   @RequestBody request: CreatePostRequest): ResponseEntity<PostBrief> {
        // 게시물 생성 로직 처리
        val post = postService.create(
            content = request.content,
            fileUrls = request.files,
            disableComment = request.hideComments,
            hideLike =  request.hideLikes,
            userId = user.id)
        return ResponseEntity.status(HttpStatus.CREATED).body(post)

    }

    @GetMapping("/posts/{postId}")
    fun getPost(@AuthenticationPrincipal user: InstagramUser, @PathVariable postId: Long) : ResponseEntity<PostDetail> {
        val postDetail = postService.get(postId = postId, userId = user.id)
        return ResponseEntity.ok(postDetail)
    }

    @DeleteMapping("/posts/{postId}")
    fun deletePost(@AuthenticationPrincipal user: InstagramUser, @PathVariable postId: Long) : ResponseEntity<Unit> {
        postService.delete(postId = postId, userId = user.id)
        return ResponseEntity.ok().build()
    }

    @PutMapping("/posts/{postId}")
    fun updatePost(@AuthenticationPrincipal user: InstagramUser, @PathVariable postId: Long, @RequestBody request: UpdatePostRequest) : ResponseEntity<PostBrief> {
        val updatedPost = postService.patch(
            postId = postId,
            content = request.content,
            user.id
        )
        return ResponseEntity.ok(updatedPost)
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

data class CreatePostRequest(
    val content: String,
    val hideComments: Boolean = false,
    val hideLikes: Boolean = false,
    val files: List<String>
)

data class UpdatePostRequest(
    val content: String
)