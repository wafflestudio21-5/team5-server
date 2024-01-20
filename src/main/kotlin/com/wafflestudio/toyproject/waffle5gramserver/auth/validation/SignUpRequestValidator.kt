package com.wafflestudio.toyproject.waffle5gramserver.auth.validation

import com.wafflestudio.toyproject.waffle5gramserver.auth.dto.SignUpRequestDto
import com.wafflestudio.toyproject.waffle5gramserver.user.repository.ContactType
import com.wafflestudio.toyproject.waffle5gramserver.user.validation.CustomPhoneValidator
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import org.hibernate.validator.internal.constraintvalidators.bv.EmailValidator

class SignUpRequestValidator : ConstraintValidator<ValidSignUpRequest, SignUpRequestDto> {
    override fun isValid(signUpRequestDto: SignUpRequestDto?, context: ConstraintValidatorContext?): Boolean {
        return when (signUpRequestDto?.contactType) {
            ContactType.EMAIL -> {
                if (EmailValidator().isValid(signUpRequestDto.contact, context)) {
                    true
                } else {
                    setValidationErrorMessage(
                        context = context,
                        fieldName = "contact",
                        reason = "올바르지 않은 이메일 형식입니다."
                    )
                    false
                }
            }
            ContactType.PHONE -> {
                if (CustomPhoneValidator().isValid(signUpRequestDto.contact, context)) {
                    true
                } else {
                    setValidationErrorMessage(
                        context = context,
                        fieldName = "contact",
                        reason = "올바르지 않은 전화번호 형식입니다."
                    )
                    false
                }
            }
            else -> {
                setValidationErrorMessage(
                    context = context,
                    fieldName = "contactType",
                    reason = "contactType은 email 또는 phone 이어야 합니다."
                )
                false
            }
        }
    }

    private fun setValidationErrorMessage(
        context: ConstraintValidatorContext?,
        fieldName: String,
        reason: String
    ) {
        context?.disableDefaultConstraintViolation()
        context?.buildConstraintViolationWithTemplate(reason)
            ?.addPropertyNode(fieldName)
            ?.addConstraintViolation()
    }
}
