package com.wafflestudio.toyproject.waffle5gramserver.global.error_handling

/*
에러코드들을 모아서 정리하고, 통합적으로 관리하기 위한 class입니다.
각 기능별로 각각의 구현 폴더에 Exception class들을 정의해주시고, 에러코드와 그 내용은 아래 양식처럼 여기에 적어서 관리하시면 됩니다.
Exception class를 정의하실 때는 BusinessException을 상속 받으신 뒤 생성자에서 super을 통해 여기에 적으신 ErrorCode를 사용하시면 편리합니다.
 */
enum class ErrorCode(
    val status: Int,
    val code: String,
    val message: String,
) {

    // Global Exceptions
    INPUT_VALUE_INVALID(400, "G001", "유효하지 않은 입력입니다."),
    INPUT_TYPE_INVALID(400, "G002", "입력 타입이 유효하지 않습니다."),
    HTTP_MESSAGE_NOT_READABLE(400, "G003", "request message body가 없거나 값 타입이 올바르지 않습니다."),
    METHOD_NOT_ALLOWED(405, "G004", "허용되지 않은 HTTP method입니다."),
    HTTP_HEADER_INVALID(400, "G005", "request header가 유효하지 않습니다."),
    FILE_CONVERT_FAIL(500, "G008", "변환할 수 없는 파일입니다."),

    // User Exceptions
    USER_NOT_FOUND(404, "U001", "존재 하지 않는 유저입니다."),
    USER_ALREADY_EXIST(409, "U002", "이미 존재하는 유저입니다."),
    ALREADY_PRIVATE(403, "U003", "이미 비공개 계정입니다."),
    ALREADY_OPEN(403, "U004", "이미 공개 계정입니다."),
    USERNAME_ALREADY_EXIST(409, "U005", "Username already exists."),
    LINK_NOT_FOUND(404, "U006", "Link not found."),
    NEW_PASSWORD_EQUAL(400, "U007", "기존 패스워드와 새로운 패스워드가 동일합니다."),
    INVALID_OLD_PASSWORD(403, "U008", "기존 패스워드가 올바르지 않습니다."),

    // Controller-level Authentication Exceptions
    REFRESH_TOKEN_NOT_FOUND(400, "A001", "refresh token이 쿠키에 존재하지 않습니다."),
    REFRESH_TOKEN_INVALID(401, "A002", "유효하지 않은 refresh token입니다."),
    TOKEN_GENERATION_FAIL(500, "A003", "토큰 재발급에 실패하였습니다."),

    // Follow Exceptions
    USER_HIMSELF(409, "F001", "User is himself."),
    FOLLOWER_HIMSELF(409, "F002", "Follower user is himself."),
    USER_NOT_PRIVATE(403, "F003", "User account is not private."),
    FOLLOWER_NOT_PRIVATE(403, "F004", "Follower user account is not private."),
    USER_PRIVATE_NOT_FOLLOWING(403, "F005", "User account is private and not following."),
    ALREADY_FOLLOW(403, "F006", "Already follow."),
    REQUEST_NOT_FOUND(404, "F007", "Request not found."),
    REQUEST_ALREADY_SENT(409, "F008", "Request is already sent."),
    USER_NOT_NON_PRIVATE(403, "F010", "User account is not non-private."),
    ALREADY_NOT_FOLLOW(404, "F011", "Already not follow."),
    ALREADY_NOT_FOLLOWER(409, "F012", "Already not follower."),
    NOT_FOLLOWER(404, "F013", "Not follower."),
    USER_NOT_FOLLOW(404, "F014", "User not follow."),

    // Post Exceptions
    POST_NOT_FOUND(404, "P001", "존재하지 않는 게시물입니다."), // 예시

    // Feed Exceptions

    // Search Exceptions
    RECENT_SEARCH_NOT_FOUND(404, "S001", "Recent search not found."),

    // Alarm Exceptions

    // other else
    // INTERNAL_SERVER_ERROR(500, "G006", "내부 서버 오류입니다."),
    S3_UPLOAD_ERROR(500, "G009", "S3 업로드에 실패하였습니다."),
    S3_DELETE_ERROR(500, "G010", "S3 삭제에 실패하였습니다."),
    INVALID_IMAGE_TYPE(403, "G010", "유효하지 않은 이미지 타입입니다."),
}
