package com.wafflestudio.toyproject.waffle5gramserver.profile.exception

import com.wafflestudio.toyproject.waffle5gramserver.global.error_handling.BusinessException
import com.wafflestudio.toyproject.waffle5gramserver.global.error_handling.ErrorCode

class ProfileEditException : BusinessException {
    constructor(errorCode: ErrorCode) : super(errorCode)
}
