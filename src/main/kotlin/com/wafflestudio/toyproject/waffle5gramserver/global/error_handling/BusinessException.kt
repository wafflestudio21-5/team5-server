package com.wafflestudio.toyproject.waffle5gramserver.global.error_handling

/*
Business layer에서 발생하는 에러들에 대해서 공통적으로 처리해주기 위한 Exception입니다.
각각의 Exception을 정의하실 때 BusinessException을 상속받으신 뒤, super()를 이용해서 원하는 형태의 생성자를 사용해주시면 됩니다.
일반적으로 쓰이는 몇개의 Exception들에 대해서는 미리 만들어놓았으니, 비슷한 방식으로 작업하셔도 됩니다.
 */
open class BusinessException : RuntimeException {

    var errorCode: ErrorCode
    var errors: List<ErrorResponse.FieldError> = ArrayList()

    constructor(message: String, errorCode: ErrorCode) : super(message) {
        this.errorCode = errorCode
    }

    constructor(errorCode: ErrorCode) : super(errorCode.message) {
        this.errorCode = errorCode
    }

    constructor(errorCode: ErrorCode, errors: List<ErrorResponse.FieldError>) : super(errorCode.message) {
        this.errors = errors
        this.errorCode = errorCode
    }
}
