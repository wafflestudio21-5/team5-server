package com.wafflestudio.toyproject.waffle5gramserver.user

import com.wafflestudio.toyproject.waffle5gramserver.user.repository.UserEntity
import com.wafflestudio.toyproject.waffle5gramserver.user.repository.UserRepository
import com.wafflestudio.toyproject.waffle5gramserver.user.service.UserPrivateService
import jakarta.transaction.Transactional
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.util.Date

@Transactional
@SpringBootTest
class UserPrivateServiceTest
@Autowired
constructor(
    private val userRepository: UserRepository,
    private val userPrivateService: UserPrivateService,
) {
    private lateinit var testUser1: UserEntity
    private lateinit var testUser2: UserEntity

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
    fun `비공개 유저를 공개 유저로 전환`() {
        // 사용할 테스트 데이터 준비
        val userId = testUser1.id
        val isPrivate = testUser1.isPrivate

        // 테스트 실행
        val userPrivateResponse = userPrivateService.toOpen(userId, isPrivate)

        // 검증
        Assertions.assertNotNull(userPrivateResponse)
        Assertions.assertEquals(userPrivateResponse.isPrivate, false)
    }

    @Test
    @Transactional
    fun `공개 유저를 비공개 유저로 전환`() {
        // 사용할 테스트 데이터 준비
        val userId = testUser2.id
        val isPrivate = testUser2.isPrivate

        // 테스트 실행
        val userPrivateResponse = userPrivateService.toPrivate(userId, isPrivate)

        // 검증
        Assertions.assertNotNull(userPrivateResponse)
        Assertions.assertEquals(userPrivateResponse.isPrivate, true)
    }
}
