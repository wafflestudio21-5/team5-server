package com.wafflestudio.toyproject.waffle5gramserver.follow

import com.wafflestudio.toyproject.waffle5gramserver.follow.repository.FollowEntity
import com.wafflestudio.toyproject.waffle5gramserver.follow.repository.FollowRepository
import com.wafflestudio.toyproject.waffle5gramserver.follow.repository.FollowRequestRepository
import com.wafflestudio.toyproject.waffle5gramserver.follow.service.FollowRetrieveService
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
class FollowRetrieveTest
@Autowired
constructor(
    private val followRetrieveService: FollowRetrieveService,
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

    private fun createFollow(
        follower: UserEntity,
        followee: UserEntity,
    ): FollowEntity {
        val follow =
            FollowEntity(
                follower = follower,
                followee = followee,
            )
        return followRepository.save(follow)
    }

    @Test
    @Transactional
    fun `팔로워 여부 조회`() {
        createFollow(testUser1, testUser2)
        followRetrieveService.getFollower(testInstaUser2, testUser1.username)
        // 에러가 발생하지 않고 제대로 실행됨
    }

    @Test
    @Transactional
    fun `유저 팔로우 여부 조회`() {
        createFollow(testUser2, testUser1)
        followRetrieveService.getFollower(testInstaUser1, testUser2.username)
        // 에러가 발생하지 않고 제대로 실행됨
    }

    @Test
    @Transactional
    fun `유저의 팔로워 및 현재 유저의 팔로잉 공통 조회`() {
        val testUser3 = createUser(false, "test3")
        val testUser4 = createUser(false, "test4")
        val testUser5 = createUser(false, "test5")

        // 팔로우 생성 (조회 대상 유저 : 1, 로그인 유저 : 2)
        createFollow(testUser2, testUser1) // 2->1
        createFollow(testUser3, testUser1) // 3->1
        createFollow(testUser4, testUser1) // 4->1
        createFollow(testUser2, testUser3) // 2->3
        createFollow(testUser2, testUser4) // 2->4
        createFollow(testUser2, testUser5) // 2->5

        val list = followRetrieveService.getCommonUserBetweenUsersFollowerAndAuthUsersFollowing(testInstaUser2, testUser1.username)
        Assertions.assertEquals(list.count, 2)
    }

    @Test
    @Transactional
    fun `유저의 팔로워 중 현재 유저의 팔로잉이 아닌 목록 조회`() {
        val testUser3 = createUser(false, "test3")
        val testUser4 = createUser(false, "test4")
        val testUser5 = createUser(false, "test5")

        // 팔로우 생성 (조회 대상 유저 : 1, 로그인 유저 : 2)
        createFollow(testUser2, testUser1) // 2->1
        createFollow(testUser3, testUser1) // 3->1
        createFollow(testUser4, testUser1) // 4->1
        createFollow(testUser2, testUser3) // 2->3
        createFollow(testUser2, testUser4) // 2->4
        createFollow(testUser2, testUser5) // 2->5

        val list = followRetrieveService.getDifferenceBetweenUsersFollowerAndAuthUsersFollowing(testInstaUser2, testUser1.username)
        Assertions.assertEquals(list.count, 1)
    }

    @Test
    @Transactional
    fun `유저의 팔로잉 및 현재 유저의 팔로잉 공통 목록 조회`() {
        val testUser3 = createUser(false, "test3")
        val testUser4 = createUser(false, "test4")
        val testUser5 = createUser(false, "test5")

        // 팔로우 생성 (조회 대상 유저 : 1, 로그인 유저 : 2)
        createFollow(testUser1, testUser2) // 1->2
        createFollow(testUser1, testUser3) // 1->3
        createFollow(testUser1, testUser4) // 1->4
        createFollow(testUser2, testUser3) // 2->3
        createFollow(testUser2, testUser4) // 2->4
        createFollow(testUser2, testUser5) // 2->5

        val list = followRetrieveService.getCommonFollowingBetweenUserAndAuthUser(testInstaUser2, testUser1.username)
        Assertions.assertEquals(list.count, 2)
    }

    @Test
    @Transactional
    fun `유저의 팔로잉 중 현재 유저의 팔로잉이 아닌 목록 조회`() {
        val testUser3 = createUser(false, "test3")
        val testUser4 = createUser(false, "test4")
        val testUser5 = createUser(false, "test5")

        // 팔로우 생성 (조회 대상 유저 : 1, 로그인 유저 : 2)
        createFollow(testUser1, testUser2) // 1->2
        createFollow(testUser1, testUser3) // 1->3
        createFollow(testUser1, testUser4) // 1->4
        createFollow(testUser2, testUser3) // 2->3
        createFollow(testUser2, testUser4) // 2->4
        createFollow(testUser2, testUser5) // 2->5

        val list = followRetrieveService.getDifferenceBetweenUsersFollowingAndAuthUsersFollowing(testInstaUser2, testUser1.username)
        Assertions.assertEquals(list.count, 1)
    }
}
