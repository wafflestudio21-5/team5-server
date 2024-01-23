package com.wafflestudio.toyproject.waffle5gramserver.global.result_handling

// ErrorCode와 동일한 방식으로 Result 응답을 정의합니다.
enum class ResultCode(
    val status: Int,
    val code: String,
    val message: String,
) {

    // User Result
    REGISTER_SUCCESS(201, "M001", "회원가입에 성공하였습니다."), // 예시
    TO_PRIVATE_CHANGE_SUCCESS(200, "M002", "공개 계정으로의 전환에 성공하였습니다."),
    TO_OPEN_CHANGE_SUCCESS(200, "M003", "비공개 계정으로의 전환에 성공하였습니다."),

    // Post Result
    GET_MEMBER_POSTS_SUCCESS(200, "P001", "회원의 게시물 조회에 성공하였습니다."),

    // Follow Result
    ALREADY_REQUEST_FOLLOW(200, "F001", "Already request follow."),
    REQUEST_FOLLOW_SUCCESS(201, "F002", "Request follow is successful."),
    DELETE_FOLLOW_SUCCESS(200, "F003", "Delete follow request is successful."),
    FOLLOW_REQUEST_LIST_SUCCESS(200, "F004", "Retrieving follow request list is successful."),
    GET_USER_FOLLOW_REQUEST_SUCCESS(200, "F005", "Retrieving user follow request is successful."),
    ACCEPT_FOLLOW_REQUEST_SUCCESS(201, "F006", "Accepting follow request is successful."),
    DECLINE_FOLLOW_REQUEST_SUCCESS(200, "F007", "Declining follow request is successful."),
    FOLLOW_SUCCESS(201, "F008", "Follow is successful."),
    UNFOLLOW_SUCCESS(200, "F009", "Unfollow is successful."),
    DELETE_FOLLOWER_SUCCESS(200, "F010", "Deleting follower is successful."),
    RETRIEVE_FOLLOWER_SUCCESS(200, "F011", "Retrieving follower is successful."),
    GET_USER_FOLLOW_SUCCESS(200, "F012", "Retrieving user follow is successful."),
    GET_COMMON_FOLLOWER_FOLLOWING(200, "F013", "Retrieving common user between user’s follower and auth user’s following is successful."),
    GET_DIFF_FOLLOWER_FOLLOWING(200, "F014", "Retrieving difference between user’s follower and auth user’s following is successful."),
    GET_COMMON_FOLLOWING_FOLLOWING(200, "F015", "Retrieving common following between user and auth user is successful."),
    GET_DIFF_FOLLOWING_FOLLOWING(200, "F016", "Retrieving difference between user’s following and auth user’s following is successful."),

    // Feed Result
    FIND_POST_PAGE_SUCCESS(200, "FE001", "게시물 목록 페이지 조회에 성공하였습니다."),

    // Search Result

    // other else
}
