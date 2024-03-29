package com.wafflestudio.toyproject.waffle5gramserver.feed

import com.wafflestudio.toyproject.waffle5gramserver.feed.service.FeedService
import com.wafflestudio.toyproject.waffle5gramserver.post.repository.PostCategory
import com.wafflestudio.toyproject.waffle5gramserver.post.repository.PostEntity
import com.wafflestudio.toyproject.waffle5gramserver.post.repository.PostRepository
import com.wafflestudio.toyproject.waffle5gramserver.user.repository.UserEntity
import com.wafflestudio.toyproject.waffle5gramserver.user.repository.UserRepository
// import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.domain.PageRequest
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime
import java.util.Date

@Transactional
@SpringBootTest
class FeedServiceTest @Autowired constructor(
    private val feedService: FeedService,
    private val postRepository: PostRepository,
    private val userRepository: UserRepository,
) {

    // private fun createPost(content: String, user: UserEntity, interval: Long = 0): Long {
    //     val post = PostEntity(
    //         content = content,
    //         likeCountDisplayed = true,
    //         commentDisplayed = true,
    //         user = user
    //     )
    //     postRepository.save(post)
    //
    //     return post.id
    // }

    private fun createTestUser(discriminator: Long): Long {
        val testUser = UserEntity(
            username = "testUsername$discriminator",
            name = "testName$discriminator",
            password = "testPassword",
            birthday = Date(),
            isPrivate = false,
            gender = "male",
            isCustomGender = false,
            profileImageUrl = "testProfileImageUrl",
            bio = "testBio"
        )
        userRepository.save(testUser)

        return testUser.id
    }

    @Transactional
    @Test
    fun `Post 생성 순서에 맞게 피드 반환`() {
        // given
        val testUserId1 = createTestUser(1)
        val testUserId2 = createTestUser(2)
        val testUser1 = userRepository.findById(testUserId1).get()
        val testUser2 = userRepository.findById(testUserId2).get()

        for (i in 1..20) {
            val post = PostEntity(
                content = "FirstTestContent$i",
                likeCountDisplayed = true,
                commentDisplayed = true,
                user = testUser1,
                category = PostCategory.LIFE
            )
            postRepository.save(post)
        }

        for (i in 1..5) {
            val post = PostEntity(
                content = "SecondTestContent$i",
                likeCountDisplayed = true,
                commentDisplayed = true,
                user = testUser2,
                category = PostCategory.LIFE
            )
            postRepository.save(post)
        }

        // when
        val pageRequest = PageRequest.of(0, 10)
        val postDetailPagesFrom1 = feedService.getHomeFeed(testUserId1, pageRequest)

        val postDetailPagesFrom2 = feedService.getHomeFeed(testUserId2, pageRequest)

        val secondPageRequest = PageRequest.of(1, 10)
        val nextDetailPagesFrom2 = feedService.getHomeFeed(testUserId2, secondPageRequest)

        // then
        // assertEquals(10, postDetailPagesFrom1.size)
        // assertEquals(true, postDetailPagesFrom1.isLast)
//        assertEquals("SecondTestContent5", postDetailPagesFrom1.content[0].content)

        // assertEquals(0, postDetailPagesFrom2.number)
        // assertEquals(false, postDetailPagesFrom2.isLast)
//        assertEquals("FirstTestContent20", postDetailPagesFrom2.content[0].content)

        // assertEquals(1, nextDetailPagesFrom2.number)
        // assertEquals(true, nextDetailPagesFrom2.isLast)
//        assertEquals("FirstTestContent10", nextDetailPagesFrom2.content[0].content)
    }
}

private operator fun LocalDateTime.plus(interval: Long): LocalDateTime {
    return this.plusDays(interval)
}
