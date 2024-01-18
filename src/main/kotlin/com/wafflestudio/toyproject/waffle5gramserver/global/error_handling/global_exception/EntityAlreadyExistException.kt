package com.wafflestudio.toyproject.waffle5gramserver.global.error_handling.global_exception

import com.wafflestudio.toyproject.waffle5gramserver.global.error_handling.BusinessException
import com.wafflestudio.toyproject.waffle5gramserver.global.error_handling.ErrorCode

/*
팔로우를 눌렀는데 이미 팔로우인 상황 등, 엔티티를 추가하려 하지만 이미 있는 상황에서 공통적으로 사용하는 Exception입니다.
해당 Exception을 상황에 맞는 ErrorCode를 넣어서 날리면, BusinessException으로 간주되어 GlobalExceptionHandler에서 handling이 일어납니다.
 */
class EntityAlreadyExistException : BusinessException {
    constructor(errorCode: ErrorCode) : super(errorCode) {
    }
}
