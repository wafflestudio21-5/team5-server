package com.wafflestudio.toyproject.waffle5gramserver

import org.assertj.core.api.AssertionsForClassTypes.assertThat
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class Waffle5gramserverApplicationTests {

    @Test
    fun contextLoads() {
    }

    @Test
    fun `Github Action 초기 세팅 테스트`() {
        val result = listOf("Github", "Action", "Worked").joinToString(separator = " ")
        assertThat(result).isEqualTo("Github Action Worked")
        assertThat(2 + 4).isEqualTo(6)
    }
}
