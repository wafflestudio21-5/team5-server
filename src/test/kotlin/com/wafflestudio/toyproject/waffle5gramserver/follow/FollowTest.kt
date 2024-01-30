package com.wafflestudio.toyproject.waffle5gramserver.follow

import com.wafflestudio.toyproject.waffle5gramserver.follow.repository.FollowRepository
import com.wafflestudio.toyproject.waffle5gramserver.follow.repository.FollowRequestRepository
import com.wafflestudio.toyproject.waffle5gramserver.follow.service.FollowService
import com.wafflestudio.toyproject.waffle5gramserver.user.repository.UserEntity
import com.wafflestudio.toyproject.waffle5gramserver.user.repository.UserRepository
import com.wafflestudio.toyproject.waffle5gramserver.user.service.InstagramUser
import jakarta.transaction.Transactional
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.time.LocalDateTime
import java.util.Date

@Transactional
@SpringBootTest
class FollowTest
@Autowired
constructor(
    private val followService: FollowService,
    private val followRepository: FollowRepository,
    private val followRequestRepository: FollowRequestRepository,
    private val userRepository: UserRepository,
) {
    private lateinit var testUser1: UserEntity
    private lateinit var testUser2: UserEntity
    private lateinit var testInstaUser1: InstagramUser
    private lateinit var testInstaUser2: InstagramUser

    @BeforeEach
    fun setup() {
        testUser1 =
            createUser(
                false,
                "testuser1"
            )
        testUser2 =
            createUser(
                false,
                "testuser2"
            )
        testInstaUser1 = InstagramUser(
            id = testUser1.id,
            username = testUser1.username,
            name = testUser1.name,
            password = testUser1.password,
            birthday = testUser1.birthday,
            isPrivate = testUser1.isPrivate,
            gender = testUser1.gender,
            isCustomGender = testUser1.isCustomGender,
            profileImageUrl = testUser1.profileImageUrl,
            bio = testUser1.bio,
            createdAt = LocalDateTime.now(),
            modifiedAt = LocalDateTime.now(),
        )
        testInstaUser2 = InstagramUser(
            id = testUser2.id,
            username = testUser2.username,
            name = testUser2.name,
            password = testUser2.password,
            birthday = testUser2.birthday,
            isPrivate = testUser2.isPrivate,
            gender = testUser2.gender,
            isCustomGender = testUser2.isCustomGender,
            profileImageUrl = testUser2.profileImageUrl,
            bio = testUser2.bio,
            createdAt = LocalDateTime.now(),
            modifiedAt = LocalDateTime.now(),
        )
    }

    private fun createUser(
        isPrivate: Boolean,
        username: String,
    ): UserEntity {
        val user =
            UserEntity(
                username = username,
                name = "Test User",
                password = "password123",
                birthday = Date(),
                isPrivate = isPrivate,
                gender = "male",
                isCustomGender = false,
                profileImageUrl = "https://wafflestudio.com/images/icon_intro.svg",
                bio = "This is a test bio",
            )
        return userRepository.save(user)
    }

    @Test
    @Transactional
    fun `공개 유저 팔로우`() {
        followService.postFollowNonPrivateUser(testInstaUser1, testUser2.username)
        // Assertions.assertEquals(followRepository.count(), 1)
    }

    @Test
    @Transactional
    fun `유저 언팔로우`() {
        followService.postFollowNonPrivateUser(testInstaUser1, testUser2.username)
        // Assertions.assertEquals(followRepository.count(), 1)
        followService.deleteFollowUser(testInstaUser1, testUser2.username)
        // Assertions.assertEquals(followRepository.count(), 0)
    }

    @Test
    @Transactional
    fun `팔로워 삭제`() {
        followService.postFollowNonPrivateUser(testInstaUser1, testUser2.username)
        // Assertions.assertEquals(followRepository.count(), 1)
        followService.removeFollower(testInstaUser2, testUser1.username)
        // Assertions.assertEquals(followRepository.count(), 0)
    }
}
