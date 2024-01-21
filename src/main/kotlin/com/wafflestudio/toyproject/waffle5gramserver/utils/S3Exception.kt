package com.wafflestudio.toyproject.waffle5gramserver.utils

import com.wafflestudio.toyproject.waffle5gramserver.global.error_handling.BusinessException
import com.wafflestudio.toyproject.waffle5gramserver.global.error_handling.ErrorCode

class S3Exception(message: String) : BusinessException(
    message = message,
    errorCode = ErrorCode.S3_ERROR
)