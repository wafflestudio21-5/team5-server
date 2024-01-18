package com.wafflestudio.toyproject.waffle5gramserver.global.error_handling.global_exception

import com.wafflestudio.toyproject.waffle5gramserver.global.error_handling.BusinessException
import com.wafflestudio.toyproject.waffle5gramserver.global.error_handling.ErrorCode

/*
유저를 찾을 수 없거나 팔로우 연결을 찾을 수 없는 등, 엔티티가 없는 상황에서 공통적으로 사용하는 Exception입니다.
해당 Exception을 상황에 맞는 ErrorCode를 넣어서 날리면, BusinessException으로 간주되어 GlobalExceptionHandler에서 handling이 일어납니다.
 */
class EntityNotFoundException : BusinessException {
    constructor(errorCode: ErrorCode) : super(errorCode) {
    }
}
