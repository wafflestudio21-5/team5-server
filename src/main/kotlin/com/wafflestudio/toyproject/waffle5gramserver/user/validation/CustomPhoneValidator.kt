package com.wafflestudio.toyproject.waffle5gramserver.user.validation

import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext

class CustomPhoneValidator : ConstraintValidator<ValidPhone, String> {
    override fun isValid(phone: String?, context: ConstraintValidatorContext?): Boolean {
        if (phone == null) return false
        val phoneRegex = "^01(?:0|1|[6-9])-(?:\\d{3}|\\d{4})-\\d{4}$|^(?:2|3[1-3]|4[1-4]|5[1-5]|6[1-4])-(?:\\d{3}|\\d{4})-\\d{4}$|^[1-5]7[0-7]-\\d{4}$"
        return phone.matches(phoneRegex.toRegex())
    }
}
