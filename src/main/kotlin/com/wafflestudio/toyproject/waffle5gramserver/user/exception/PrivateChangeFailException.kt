package com.wafflestudio.toyproject.waffle5gramserver.user.exception

import com.wafflestudio.toyproject.waffle5gramserver.global.error_handling.BusinessException
import com.wafflestudio.toyproject.waffle5gramserver.global.error_handling.ErrorCode

class PrivateChangeFailException : BusinessException {
    constructor(errorCode: ErrorCode) : super(errorCode)
}
