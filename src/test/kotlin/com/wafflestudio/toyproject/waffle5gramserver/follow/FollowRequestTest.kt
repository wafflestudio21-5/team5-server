package com.wafflestudio.toyproject.waffle5gramserver.follow

import com.wafflestudio.toyproject.waffle5gramserver.follow.repository.FollowRepository
import com.wafflestudio.toyproject.waffle5gramserver.follow.repository.FollowRequestEntity
import com.wafflestudio.toyproject.waffle5gramserver.follow.repository.FollowRequestRepository
import com.wafflestudio.toyproject.waffle5gramserver.follow.service.FollowRequestService
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
class FollowRequestTest
@Autowired
constructor(
    private val followRequestService: FollowRequestService,
    private val followRequestRepository: FollowRequestRepository,
    private val followRepository: FollowRepository,
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
                true,
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

    private fun createFollowRequest(
        follower: UserEntity,
        followee: UserEntity,
    ): FollowRequestEntity {
        val followRequest =
            FollowRequestEntity(
                follower = follower,
                followee = followee,
            )
        return followRequestRepository.save(followRequest)
    }

    @Test
    @Transactional
    fun `비공개 유저 팔로우 요청 조회`() {
        createFollowRequest(testUser2, testUser1)
        val followRequestResponse = followRequestService.getFollowRequestToPrivateUser(testInstaUser2, testUser1.username)

        Assertions.assertEquals(followRequestResponse.followerUserId, testInstaUser2.id)
    }

    @Test
    @Transactional
    fun `비공개 유저 팔로우 요청`() {
        val followRequestResponse = followRequestService.postFollowToPrivateUser(testInstaUser2, testUser1.username)
        val followRequestResponseSearch = followRequestService.getFollowRequestToPrivateUser(testInstaUser2, testUser1.username)
        Assertions.assertEquals(followRequestResponse, followRequestResponseSearch)
    }

    @Test
    @Transactional
    fun `비공개 유저 팔로우 요청 취소`() {
        createFollowRequest(testUser2, testUser1)
        followRequestService.removeFollowRequestToPrivateUser(testInstaUser2, testUser1.username)
        // 정상 작동 확인 완료
    }

    @Test
    @Transactional
    fun `팔로우 요청 유저 목록 조회`() {
        var testUser3 = createUser(false, "testUser3")
        createFollowRequest(testUser2, testUser1)
        createFollowRequest(testUser3, testUser1)
        val number = followRequestService.getFollowRequestUserList(testInstaUser1).count()
        Assertions.assertEquals(number, 2)
    }

    @Test
    @Transactional
    fun `유저 팔로우 요청 조회`() {
        createFollowRequest(testUser2, testUser1)
        val followRequestResponse = followRequestService.getUserFollowRequest(testInstaUser1, testUser2.username)
        Assertions.assertEquals(followRequestResponse.followerUserId, testUser2.id)
        Assertions.assertEquals(followRequestResponse.followeeUserId, testUser1.id)
    }

    @Test
    @Transactional
    fun `팔로우 요청 수락`() {
        createFollowRequest(testUser2, testUser1)
        // val followResponse = followRequestService.postFollowRequest(testInstaUser1, testUser2.username)
        Assertions.assertEquals(followRepository.count(), 1)
        Assertions.assertEquals(followRequestRepository.count(), 0)
    }

    @Test
    @Transactional
    fun `팔로우 요청 거절`() {
        createFollowRequest(testUser2, testUser1)
        followRequestService.removeFollowRequest(testInstaUser1, testUser2.username)
        Assertions.assertEquals(followRequestRepository.count(), 0)
    }
}
