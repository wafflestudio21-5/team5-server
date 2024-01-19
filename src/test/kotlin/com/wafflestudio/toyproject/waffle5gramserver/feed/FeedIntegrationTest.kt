package com.wafflestudio.toyproject.waffle5gramserver.feed

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.transaction.annotation.Transactional

@Transactional
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class FeedIntegrationTest @Autowired constructor(
    private val mvc: MockMvc
) {
    @Test
    fun `피드 테스트`() {
        val token = obtainBearerToken("user-0", "password-0")

        mvc.perform(
            MockMvcRequestBuilders.get("/api/v1/feed/timeline")
                .header("Authorization", "Bearer $token")
                .param("page", "1")
                .param("size", "10")
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isOk)
    }

    private fun obtainBearerToken(username: String, password: String): String {
        val response = mvc.perform(
            MockMvcRequestBuilders.post("/api/v1/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""{"username": "$username", "password": "$password"}""")
        )
            .andExpect(status().isCreated)
            .andReturn()

        return response.response.contentAsString.substringAfter(":").substringBefore("}").trim('"')
    }
}