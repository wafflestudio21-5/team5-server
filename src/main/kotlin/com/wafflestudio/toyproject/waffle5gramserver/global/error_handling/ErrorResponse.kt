package com.wafflestudio.toyproject.waffle5gramserver.global.error_handling

import jakarta.validation.ConstraintViolation
import org.springframework.validation.BindingResult
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException
import java.util.ArrayList
import java.util.stream.Collectors
/*
GlobalExeptionHandler에서는 GlobalException부터 BusinessException, ValidationException까지 모든 종류의 에러에 대한 Response를 관리합니다.
이때 에러도 정상 응답과 마찬가지로 ResponseEntity를 이용해서 응답을 내리고자 하는데, 그때 에러의 응답 객체로 사용되는 것이 ErrorResponse 클래스입니다.
ErrorResponse의 사용 양상 상 인스턴스를 만들지 않고 메서드를 사용해야 하기에 모든 메서드를 static으로 정의했습니다.
해당 응답 객체를 통해 에러의 status number, code, 에러 메시지를 효과적으로 전달할 수 있습니다.
 */
class ErrorResponse {

    var status: Int = 0
    var code: String = ""
    var message: String = ""
    var errors: List<FieldError> = arrayListOf()

    constructor(errorcode: ErrorCode, errors: List<FieldError>) {
        this.status = errorcode.status
        this.code = errorcode.code
        this.message = errorcode.message
        this.errors = errors
    }

    constructor(errorcode: ErrorCode) {
        this.status = errorcode.status
        this.code = errorcode.code
        this.message = errorcode.message
    }

    companion object {
        @JvmStatic
        fun of(code: ErrorCode): ErrorResponse {
            return ErrorResponse(code)
        } // 에러코드만 있는 경우

        @JvmStatic
        fun of(code: ErrorCode, errors: List<FieldError>): ErrorResponse {
            return ErrorResponse(code, errors)
        } // 에러코드와 에러가 같이 있는 경우

        @JvmStatic
        fun of(code: ErrorCode, bindingResult: BindingResult): ErrorResponse {
            return ErrorResponse(code, FieldError.of(bindingResult))
        } // @Valid, @Validated annotation에 의한 유효성 검사에서 발생하는 에러들의 응답을 정의 - bindingResult를 직접 받는 경우

        @JvmStatic
        fun of(code: ErrorCode, constraintViolations: Set<ConstraintViolation<*>>): ErrorResponse {
            return ErrorResponse(code, FieldError.of(constraintViolations))
        } // @Valid, @Validated annotation에 의한 유효성 검사에서 발생하는 에러들의 응답을 정의 - ConstraintViolation 객체를 받는 경우

        @JvmStatic
        fun of(code: ErrorCode, missingParameterName: String): ErrorResponse {
            return ErrorResponse(code, FieldError.of(missingParameterName, "", "parameter가 누락됨"))
        } // 필요한 파라미터가 누락시 발생하는 에러들의 응답을 정의

        @JvmStatic
        fun of(e: MethodArgumentTypeMismatchException): ErrorResponse {
            val value = e.value?.toString() ?: ""
            val errors = FieldError.of(e.name, value, e.errorCode)
            return ErrorResponse(ErrorCode.INPUT_TYPE_INVALID, errors)
        } // MethodArgumentTypeMismatchException 처리
    }

    data class FieldError( // 에러 응답 종류에 따른 메소드 사용을 위한 FieldError class를 따로 정의
        var field: String,
        var value: String,
        var reason: String
    ) {
        companion object {
            @JvmStatic
            fun of(field: String, value: String, reason: String): List<FieldError> {
                return listOf(FieldError(field, value, reason))
            }

            @JvmStatic
            fun of(bindingResult: BindingResult): List<FieldError> {
                val fieldErrors: List<org.springframework.validation.FieldError> = bindingResult.fieldErrors
                return fieldErrors.stream()
                    .map { error ->
                        FieldError(
                            error.field, error.rejectedValue?.toString() ?: "", error.defaultMessage ?: ""
                        )
                    }.collect(Collectors.toList())
            }

            @JvmStatic
            fun of(constraintViolations: Set<ConstraintViolation<*>>): List<FieldError> {
                val lists: List<ConstraintViolation<*>> = ArrayList(constraintViolations)
                return lists.stream()
                    .map { error ->
                        val invalidValue = error.invalidValue?.toString() ?: ""
                        val index = error.propertyPath.toString().indexOf(".")
                        val propertyPath = error.propertyPath.toString().substring(index + 1)
                        FieldError(propertyPath, invalidValue, error.message ?: "")
                    }.collect(Collectors.toList())
            }
        }
    }
}
