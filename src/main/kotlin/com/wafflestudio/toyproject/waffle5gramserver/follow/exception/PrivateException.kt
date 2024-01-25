package com.wafflestudio.toyproject.waffle5gramserver.follow.exception

import com.wafflestudio.toyproject.waffle5gramserver.global.error_handling.BusinessException
import com.wafflestudio.toyproject.waffle5gramserver.global.error_handling.ErrorCode

class PrivateException : BusinessException {
    constructor(errorCode: ErrorCode) : super(errorCode)
}
