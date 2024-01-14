package com.wafflestudio.toyproject.waffle5gramserver.global.error_handling

/*
에러코드들을 모아서 정리하고, 통합적으로 관리하기 위한 class입니다.
각 기능별로 각각의 폴더에 Exception class들을 정의해주시고, 에러코드와 그 내용은 아래 양식처럼 여기에 적어서 관리하시면 됩니다.
Exception class를 정의하실 때는 BusinessException을 상속 받으신 뒤 생성자에서 super을 통해 여기에 적으신 ErrorCode를 사용하시면 편리합니다.
 */
enum class ErrorCode(val status: Int,
                     val code: String,
                     val message: String,
) {

    //Global Exceptions
    INPUT_VALUE_INVALID(400, "G001", "유효하지 않은 입력입니다."),
    INPUT_TYPE_INVALID(400, "G002", "입력 타입이 유효하지 않습니다."),

    //User Exceptions


    //Private Exceptions


    //Follow Exceptions


    //Post Exceptions


    //other else

}