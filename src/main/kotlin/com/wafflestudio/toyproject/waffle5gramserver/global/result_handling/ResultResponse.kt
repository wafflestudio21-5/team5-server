package com.wafflestudio.toyproject.waffle5gramserver.global.result_handling

// ErrorResponse처럼 성공적인 결과 응답에 대한 획일화된 데이터 모델을 정의합니다.
class ResultResponse {

    private var status: Int // Http 상태 코드
    private var code: String // Business 상태 코드
    private var message: String // 응답 메시지
    private var data: Any // 응답 데이터

    constructor(resultCode: ResultCode, data: Any) {
        this.status = resultCode.status
        this.code = resultCode.code
        this.message = resultCode.message
        this.data = data
    }

    companion object {
        @JvmStatic
        fun of(resultCode: ResultCode, data: Any): ResultResponse {
            return ResultResponse(resultCode, data)
        }

        @JvmStatic
        fun of(resultCode: ResultCode): ResultResponse {
            return ResultResponse(resultCode, "")
        }
    }
}
