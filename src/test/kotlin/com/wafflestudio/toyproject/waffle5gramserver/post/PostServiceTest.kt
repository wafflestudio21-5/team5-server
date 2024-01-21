package com.wafflestudio.toyproject.waffle5gramserver.post

import com.wafflestudio.toyproject.waffle5gramserver.post.repository.PostEntity
import com.wafflestudio.toyproject.waffle5gramserver.post.repository.PostRepository
import com.wafflestudio.toyproject.waffle5gramserver.post.service.PostNotAuthorizedException
import com.wafflestudio.toyproject.waffle5gramserver.post.service.PostNotFoundException
import com.wafflestudio.toyproject.waffle5gramserver.post.service.PostService
import com.wafflestudio.toyproject.waffle5gramserver.user.repository.UserEntity
import com.wafflestudio.toyproject.waffle5gramserver.user.repository.UserRepository
import jakarta.transaction.Transactional
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.time.LocalDateTime
import java.util.Date

@Transactional
@SpringBootTest
class PostServiceTest
@Autowired
constructor(
    private val postService: PostService,
    private val postRepository: PostRepository,
    private val userRepository: UserRepository,
) {
    private lateinit var testUser1: UserEntity
    private lateinit var testUser2: UserEntity

    @BeforeEach
    fun setup() {
        testUser1 =
            createUser(
                "testuser1",
                "Test User",
            )
        testUser2 =
            createUser(
                "testuser2",
                "Test User2",
            )
    }

    private fun createUser(
        username: String,
        name: String,
    ): UserEntity {
        val user =
            UserEntity(
                username = username,
                name = name,
                password = "password123",
                birthday = Date(),
                isPrivate = false,
                pronoun = "they/them",
                profileImageUrl = "https://wafflestudio.com/images/icon_intro.svg",
                bio = "This is a test bio",
            )
        return userRepository.save(user)
    }

    private fun createPost(
        content: String,
        user: UserEntity,
    ): PostEntity {
        val post =
            PostEntity(
                content = content,
                likeCountDisplayed = true,
                commentDisplayed = true,
                createdAt = LocalDateTime.now(),
                modifiedAt = LocalDateTime.now(),
                user = user,
            )
        return postRepository.save(post)
    }

    @Test
    @Transactional
    fun `create post with valid data`() {
        // 사용할 테스트 데이터 준비
        val content = "Test Content"
        val fileUrls = listOf("https://wafflestudio.com/images/icon_intro.svg")
        val disableComment = false
        val hideLike = false

        // 테스트 실행
        val postBrief = postService.create(content, fileUrls, disableComment, hideLike, testUser1.id)

        // 검증
        assertNotNull(postBrief)
        assertEquals(content, postBrief.content)
        // 추가적인 결과값 검증
        // 생성된 게시물이 DB에 저장되었는지 검증
        val post = postRepository.findById(postBrief.id).orElseThrow { Exception("Post not found") }
//        assertEquals(1, post.id)
    }

    @Test
    fun `update post content`() {
        val post = createPost("Original Content", testUser1)
        val updatedContent = "Updated Content"

        postService.patch(post.id, updatedContent, testUser1.id)

        val updatedPost = postRepository.findById(post.id).orElseThrow { Exception("Post not found") }
        assertEquals(updatedContent, updatedPost.content)
    }

    @Test
    fun `throw exception when user tries to update a post they don't own`() {
        val post =
            PostEntity(
                id = 1L,
                content = "Original Content",
                likeCountDisplayed = true,
                commentDisplayed = true,
                createdAt = LocalDateTime.now(),
                modifiedAt = LocalDateTime.now(),
                user = testUser1,
            )
        postRepository.save(post)

        assertThrows<PostNotAuthorizedException> {
            postService.patch(post.id, "New Content", testUser2.id)
        }
    }

    @Test
    fun `delete post`() {
        val post = createPost("Original Content", testUser1)
        postService.delete(post.id, testUser1.id)
        assertFalse(postRepository.existsById(post.id))
    }

    @Test
    fun `throw exception when user tries to delete a post they don't own`() {
        val post = createPost("Original Content", testUser1)

        assertThrows<PostNotAuthorizedException> {
            postService.delete(post.id, testUser2.id)
        }
    }

    @Test
    fun `patch with non-existing post id throws PostNotFoundException`() {
        val nonExistingPostId = 999L // 존재하지 않는 게시물 ID
        val userId = 1L
        val newContent = "Updated Content"

        assertThrows<PostNotFoundException> {
            postService.patch(nonExistingPostId, newContent, userId)
        }
    }

    @Test
    fun `delete non-existing post throws PostNotFoundException`() {
        val nonExistingPostId = 999L // 존재하지 않는 게시물 ID
        val userId = 1L

        assertThrows<PostNotFoundException> {
            postService.delete(nonExistingPostId, userId)
        }
    }

    @Test
    fun `delete post with transaction rollback`() {
        val post = createPost("Original Content", testUser1)

        // 실패를 유발하는 상황을 만듦 (예: 유효하지 않은 사용자 ID 사용)
        val invalidUserId = testUser1.id + 1

        assertThrows<Exception> {
            postService.delete(post.id, invalidUserId)
        }

        // 롤백 확인: 게시물이 여전히 존재하는지 확인
        assertTrue(postRepository.existsById(post.id))
    }
}
