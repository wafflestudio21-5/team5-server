package com.wafflestudio.toyproject.waffle5gramserver.global.error_handling

import com.wafflestudio.toyproject.waffle5gramserver.global.error_handling.ErrorCode.HTTP_HEADER_INVALID
import com.wafflestudio.toyproject.waffle5gramserver.global.error_handling.ErrorCode.HTTP_MESSAGE_NOT_READABLE
import com.wafflestudio.toyproject.waffle5gramserver.global.error_handling.ErrorCode.INPUT_VALUE_INVALID
import com.wafflestudio.toyproject.waffle5gramserver.global.error_handling.ErrorCode.INTERNAL_SERVER_ERROR
import com.wafflestudio.toyproject.waffle5gramserver.global.error_handling.ErrorCode.METHOD_NOT_ALLOWED
import jakarta.validation.ConstraintViolationException
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatus.BAD_REQUEST
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.validation.BindException
import org.springframework.web.HttpRequestMethodNotSupportedException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.MissingRequestCookieException
import org.springframework.web.bind.MissingServletRequestParameterException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException
import org.springframework.web.multipart.support.MissingServletRequestPartException

/*
에러처리 컨벤션에 따른 모든 오류들의 발생과 그 응답을 제어합니다.
 */

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler
    protected fun handleException(e: Exception): ResponseEntity<ErrorResponse> {
        val response: ErrorResponse = ErrorResponse.of(INTERNAL_SERVER_ERROR)
        return ResponseEntity<ErrorResponse>(response, HttpStatus.INTERNAL_SERVER_ERROR)
    } // 정체불명의 모든 내부 서버 오류를 handle

    @ExceptionHandler
    protected fun handleBusinessException(e: BusinessException): ResponseEntity<ErrorResponse> {
        val errorCode: ErrorCode = e.errorCode
        val response: ErrorResponse = ErrorResponse.of(errorCode, e.errors)
        return ResponseEntity<ErrorResponse>(response, HttpStatus.valueOf(errorCode.status))
    } // Business layer에서 발생하는 모든 오류를 BusinessException으로 정의했고, 이를 handle

    // 아래부터는 Global하게 발생할 수 있는(Controller 단에서 발생하는) 에러들의 종류와 그 handling입니다.

    @ExceptionHandler
    protected fun handleHttpRequestMethodNotSupportedException(e: HttpRequestMethodNotSupportedException): ResponseEntity<ErrorResponse> {
        val errors: MutableList<ErrorResponse.FieldError> = ArrayList()
        errors.add(ErrorResponse.FieldError("http method", e.method, METHOD_NOT_ALLOWED.message))
        val response: ErrorResponse = ErrorResponse.of(HTTP_HEADER_INVALID, errors)
        return ResponseEntity<ErrorResponse>(response, BAD_REQUEST)
    } // 잘못된 타입의 http 요청으로 받아들였을 때 발생하는 오류를 handle

    @ExceptionHandler
    protected fun handleMissingServletRequestParameterException(e: MissingServletRequestParameterException): ResponseEntity<ErrorResponse> {
        val response: ErrorResponse = ErrorResponse.of(INPUT_VALUE_INVALID, e.parameterName)
        return ResponseEntity<ErrorResponse>(response, BAD_REQUEST)
    } // @RequestParam annotation 사용시, 해당 파라미터가 들어오지 않았을 때 발생하는 오류를 handle

    @ExceptionHandler
    protected fun handleMethodArgumentTypeMismatchException(e: MethodArgumentTypeMismatchException): ResponseEntity<ErrorResponse> {
        val response: ErrorResponse = ErrorResponse.of(e)
        return ResponseEntity<ErrorResponse>(response, BAD_REQUEST)
    } // Controller가 받는 파라미터의 타입이 잘못되었을 때 발생하는 오류를 handle

    @ExceptionHandler
    protected fun handleConstraintViolationException(e: ConstraintViolationException): ResponseEntity<ErrorResponse> {
        val response: ErrorResponse = ErrorResponse.of(INPUT_VALUE_INVALID, e.constraintViolations)
        return ResponseEntity<ErrorResponse>(response, BAD_REQUEST)
    } // @Valid 및 @Validation annotation 사용시, 데이터 유효성 검증을 만족하지 않아 발생하는 오류를 handle

    @ExceptionHandler
    protected fun handleMethodArgumentNotValidException(e: MethodArgumentNotValidException): ResponseEntity<ErrorResponse> {
        val response: ErrorResponse = ErrorResponse.of(INPUT_VALUE_INVALID, e.bindingResult)
        return ResponseEntity<ErrorResponse>(response, BAD_REQUEST)
    } // Controller의 파라미터에 @Valid 및 @Validation annotation 사용시, 정의한 제약조건을 어겨서 발생하는 오류를 handle

    @ExceptionHandler
    protected fun handleBindException(e: BindException): ResponseEntity<ErrorResponse> {
        val response: ErrorResponse = ErrorResponse.of(INPUT_VALUE_INVALID, e.bindingResult)
        return ResponseEntity<ErrorResponse>(response, BAD_REQUEST)
    } // 데이터 바인딩 및 검증과정에서 발생하는 오류를 handle

    @ExceptionHandler
    protected fun handleHttpMessageNotReadableException(e: HttpMessageNotReadableException): ResponseEntity<ErrorResponse> {
        val response: ErrorResponse = ErrorResponse.of(HTTP_MESSAGE_NOT_READABLE)
        return ResponseEntity<ErrorResponse>(response, BAD_REQUEST)
    } // Http Request body에 들어오는 데이터의 자료형이 잘못되거나 필수 값이 누락된 경우 발생하는 오류를 handle

    // 아래도 마찬가지로 global한 환경에서 발생하는 오류이지만, 아직 사용하지 않는 일반적인 오류들을 대상으로 합니다.
    @ExceptionHandler
    protected fun handleMissingServletRequestPartException(e: MissingRequestCookieException): ResponseEntity<ErrorResponse> {
        val response: ErrorResponse = ErrorResponse.of(INPUT_VALUE_INVALID, e.cookieName)
        return ResponseEntity<ErrorResponse>(response, BAD_REQUEST)
    } // 요청 쿠키가 잘못되었을 때 발생하는 오류를 handle

    @ExceptionHandler
    protected fun handleMissingServletRequestPartException(e: MissingServletRequestPartException): ResponseEntity<ErrorResponse> {
        val response: ErrorResponse = ErrorResponse.of(INPUT_VALUE_INVALID, e.requestPartName)
        return ResponseEntity<ErrorResponse>(response, BAD_REQUEST)
    } // 파일 업로드를 위해 MultiparFile 사용 시 이미지 업로드에 실패하여 발생하는 오류를 handle
}
