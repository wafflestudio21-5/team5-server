package com.wafflestudio.toyproject.waffle5gramserver.global.result_handling

// ErrorCode와 동일한 방식으로 Result 응답을 정의합니다.
enum class ResultCode(
    val status: Int,
    val code: String,
    val message: String,
) {

    // User Result
    REGISTER_SUCCESS(200, "M001", "회원가입에 성공하였습니다."), // 예시

    // Post Result

    // Follow Result

    // Feed Result

    // Search Result

    // other else
}
