package com.wafflestudio.toyproject.waffle5gramserver.auth.validation

import jakarta.validation.Constraint
import kotlin.reflect.KClass

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [SignUpRequestValidator::class])
annotation class ValidSignUpRequest(
    val message: String = "Invalid sign up request",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Any>> = []
)
