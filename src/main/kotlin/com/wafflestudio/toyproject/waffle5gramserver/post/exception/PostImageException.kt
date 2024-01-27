package com.wafflestudio.toyproject.waffle5gramserver.post.exception

import com.wafflestudio.toyproject.waffle5gramserver.global.error_handling.BusinessException
import com.wafflestudio.toyproject.waffle5gramserver.global.error_handling.ErrorCode

class PostImageException : BusinessException {
    constructor(errorCode: ErrorCode) : super(errorCode)
}