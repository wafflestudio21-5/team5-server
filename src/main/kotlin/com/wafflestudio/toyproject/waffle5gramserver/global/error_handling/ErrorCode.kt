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
    INTERNAL_SERVER_ERROR(500, "G006", "내부 서버 오류입니다."),
    ENTITY_TYPE_INVALID(500, "G007", "올바르지 않은 entity type 입니다."),
    FILE_CONVERT_FAIL(500, "G008", "변환할 수 없는 파일입니다."),

    // User Exceptions
    MEMBER_NOT_FOUND(404, "M001", "존재 하지 않는 유저입니다."), // 예시

    // Follow Exceptions
    FOLLOW_ALREADY_EXIST(400, "F001", "이미 팔로우한 유저입니다."), // 예시

    // Post Exceptions
    POST_NOT_FOUND(404, "P001", "존재하지 않는 게시물입니다."), // 예시

    // Feed Exceptions

    // Search Exceptions

    // Alarm Exceptions

    // other else
}