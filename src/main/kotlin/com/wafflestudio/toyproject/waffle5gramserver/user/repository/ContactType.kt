package com.wafflestudio.toyproject.waffle5gramserver.user.repository

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonValue

enum class ContactType {
    PHONE, EMAIL;

    companion object {
        @JsonCreator
        @JvmStatic
        fun fromValue(value: String): ContactType {
            return when (value.lowercase()) {
                "phone" -> PHONE
                "email" -> EMAIL
                else -> throw IllegalArgumentException("Invalid contact type")
            }
        }
    }

    @JsonValue
    fun toValue(): String {
        return this.name.lowercase()
    }
}
