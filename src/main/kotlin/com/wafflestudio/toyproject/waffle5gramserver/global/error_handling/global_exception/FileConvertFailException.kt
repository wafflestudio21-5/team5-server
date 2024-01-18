package com.wafflestudio.toyproject.waffle5gramserver.global.error_handling.global_exception

import com.wafflestudio.toyproject.waffle5gramserver.global.error_handling.BusinessException
import com.wafflestudio.toyproject.waffle5gramserver.global.error_handling.ErrorCode

/*
올바르지 않은 이미지 형식의 파일이 올라와서 변환할 수 없을 때 사용하는 Exception입니다.
해당 Exception 날리면(ErrorCode 넣을 필요 x), BusinessException으로 간주되어 GlobalExceptionHandler에서 handling이 일어납니다.
 */

class FileConvertFailException : BusinessException {
    constructor() : super(ErrorCode.FILE_CONVERT_FAIL) {
    }
}
