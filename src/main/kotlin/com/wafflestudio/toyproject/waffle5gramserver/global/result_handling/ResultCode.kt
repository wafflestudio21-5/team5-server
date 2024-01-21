package com.wafflestudio.toyproject.waffle5gramserver.global.result_handling

// ErrorCode와 동일한 방식으로 Result 응답을 정의합니다.
enum class ResultCode(
    val status: Int,
    val code: String,
    val message: String,
) {

    // User Result
    REGISTER_SUCCESS(201, "M001", "회원가입에 성공하였습니다."), // 예시

    // Post Result
    GET_MEMBER_POSTS_SUCCESS(200, "P001", "회원의 게시물 조회에 성공하였습니다."),

    // Follow Result
    FOLLOW_SUCCESS(201, "F001", "회원 팔로우를 성공하였습니다."),

    // Feed Result
    FIND_POST_PAGE_SUCCESS(200, "FE001", "게시물 목록 페이지 조회에 성공하였습니다."),

    // Search Result

    // other else
}
