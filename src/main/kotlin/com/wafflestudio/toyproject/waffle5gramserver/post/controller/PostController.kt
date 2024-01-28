package com.wafflestudio.toyproject.waffle5gramserver.post.controller

import com.wafflestudio.toyproject.waffle5gramserver.post.service.PostAlreadyLikedException
import com.wafflestudio.toyproject.waffle5gramserver.post.service.PostAlreadySavedException
import com.wafflestudio.toyproject.waffle5gramserver.post.service.PostBrief
import com.wafflestudio.toyproject.waffle5gramserver.post.service.PostDetail
import com.wafflestudio.toyproject.waffle5gramserver.post.service.PostException
import com.wafflestudio.toyproject.waffle5gramserver.post.service.PostImageService
import com.wafflestudio.toyproject.waffle5gramserver.post.service.PostLikeService
import com.wafflestudio.toyproject.waffle5gramserver.post.service.PostNotAuthorizedException
import com.wafflestudio.toyproject.waffle5gramserver.post.service.PostNotFoundException
import com.wafflestudio.toyproject.waffle5gramserver.post.service.PostNotLikedException
import com.wafflestudio.toyproject.waffle5gramserver.post.service.PostNotSavedException
import com.wafflestudio.toyproject.waffle5gramserver.post.service.PostSaveService
import com.wafflestudio.toyproject.waffle5gramserver.post.service.PostService
import com.wafflestudio.toyproject.waffle5gramserver.post.service.UserNotFoundException
import com.wafflestudio.toyproject.waffle5gramserver.user.service.InstagramUser
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
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
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/api/v1")
class PostController(
    private val postService: PostService,
    private val postImageService: PostImageService,
    private val postLikeService: PostLikeService,
    private val postSaveService: PostSaveService,
) {
    @PostMapping(value = ["/posts"], consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun createPost(
        @AuthenticationPrincipal user: InstagramUser,
        @RequestPart("content") content: String,
        @RequestPart("hideComments") hideComments: String = "false",
        @RequestPart("hideLikes") hideLikes: String = "false",
        @RequestPart("files") files: List<MultipartFile>,
    ): ResponseEntity<PostBrief> {
        val imageUrls = postImageService.uploadImages(files)

        // 게시물 생성 로직 처리
        val post =
            postService.create(
                content = content,
                fileUrls = imageUrls,
                disableComment = hideComments.toBoolean(),
                hideLike = hideLikes.toBoolean(),
                userId = user.id,
            )

        return ResponseEntity.status(HttpStatus.CREATED).body(post)
    }

    @GetMapping("/posts/{postId}")
    fun getPost(
        @AuthenticationPrincipal user: InstagramUser,
        @PathVariable postId: Long,
    ): ResponseEntity<PostDetail> {
        val postDetail = postService.get(postId = postId, userId = user.id)
        return ResponseEntity.ok(postDetail)
    }

    @DeleteMapping("/posts/{postId}")
    fun deletePost(
        @AuthenticationPrincipal user: InstagramUser,
        @PathVariable postId: Long,
    ): ResponseEntity<Unit> {
        postService.delete(postId = postId, userId = user.id)
        return ResponseEntity.ok().build()
    }

    @PutMapping("/posts/{postId}")
    fun updatePost(
        @AuthenticationPrincipal user: InstagramUser,
        @PathVariable postId: Long,
        @RequestBody request: UpdatePostRequest,
    ): ResponseEntity<PostBrief> {
        val updatedPost =
            postService.patch(
                postId = postId,
                content = request.content,
                user.id,
            )
        return ResponseEntity.ok(updatedPost)
    }

    @PostMapping("/posts/{postId}/likes")
    fun createPostLike(
        @AuthenticationPrincipal user: InstagramUser,
        @PathVariable postId: Long,
    ): ResponseEntity<Unit> {
        postLikeService.create(postId, user.id)
        return ResponseEntity.status(HttpStatus.CREATED).build()
    }

    @DeleteMapping("/posts/{postId}/likes")
    fun deletePostLike(
        @AuthenticationPrincipal user: InstagramUser,
        @PathVariable postId: Long,
    ): ResponseEntity<Unit> {
        postLikeService.delete(postId, user.id)
        return ResponseEntity.noContent().build()
    }

    @PostMapping("/posts/{postId}/saves")
    fun createPostSave(
        @AuthenticationPrincipal user: InstagramUser,
        @PathVariable postId: Long,
    ): ResponseEntity<Unit> {
        postSaveService.create(postId, user.id)
        return ResponseEntity.status(HttpStatus.CREATED).build()
    }

    @DeleteMapping("/posts/{postId}/saves")
    fun deletePostSave(
        @AuthenticationPrincipal user: InstagramUser,
        @PathVariable postId: Long,
    ): ResponseEntity<Unit> {
        postSaveService.delete(postId, user.id)
        return ResponseEntity.noContent().build()
    }

    @ExceptionHandler(PostException::class)
    fun handleException(e: PostException): ResponseEntity<Any> {
        return when (e) {
            is PostNotFoundException -> ResponseEntity.notFound().build()
            is PostNotAuthorizedException -> ResponseEntity.status(403).build()
            is UserNotFoundException -> ResponseEntity.status(404).build()
            is PostAlreadyLikedException, is PostNotLikedException, is PostAlreadySavedException, is PostNotSavedException ->
                ResponseEntity.status(
                    409,
                ).build()
        }
    }
}

data class UpdatePostRequest(
    val content: String,
)
