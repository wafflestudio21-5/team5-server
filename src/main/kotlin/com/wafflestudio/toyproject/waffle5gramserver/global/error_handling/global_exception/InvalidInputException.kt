package com.wafflestudio.toyproject.waffle5gramserver.global.error_handling.global_exception

import com.wafflestudio.toyproject.waffle5gramserver.global.error_handling.BusinessException
import com.wafflestudio.toyproject.waffle5gramserver.global.error_handling.ErrorCode
import com.wafflestudio.toyproject.waffle5gramserver.global.error_handling.ErrorResponse

/*
Input 값이 유효하지 않을때 발생하는 에러들을 관리합니다.
해당 Exception을 상황에 맞는 error를 넣어서 날리면, BusinessException으로 간주되어 GlobalExceptionHandler에서 handling이 일어납니다.
 */
class InvalidInputException : BusinessException {
    constructor(errors: List<ErrorResponse.FieldError>) : super(ErrorCode.INPUT_VALUE_INVALID, errors) {
    }
}
