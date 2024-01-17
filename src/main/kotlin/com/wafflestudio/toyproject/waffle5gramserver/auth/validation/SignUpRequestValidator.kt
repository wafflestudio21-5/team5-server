package com.wafflestudio.toyproject.waffle5gramserver.auth.validation

import com.wafflestudio.toyproject.waffle5gramserver.auth.dto.SignUpRequestDto
import com.wafflestudio.toyproject.waffle5gramserver.user.repository.ContactType
import com.wafflestudio.toyproject.waffle5gramserver.user.validation.CustomPhoneValidator
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import org.hibernate.validator.internal.constraintvalidators.bv.EmailValidator

class SignUpRequestValidator: ConstraintValidator<ValidSignUpRequest, SignUpRequestDto> {
    override fun isValid(signUpRequestDto: SignUpRequestDto?, context: ConstraintValidatorContext?): Boolean {
        if (signUpRequestDto == null) return false
        return when (signUpRequestDto.contactType) {
            ContactType.EMAIL -> EmailValidator().isValid(signUpRequestDto.contact, context)
            ContactType.PHONE -> CustomPhoneValidator().isValid(signUpRequestDto.contact, context)
        }
    }
}
