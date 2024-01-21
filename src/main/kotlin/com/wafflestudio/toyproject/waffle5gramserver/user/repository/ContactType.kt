package com.wafflestudio.toyproject.waffle5gramserver.user.repository

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonValue

enum class ContactType {
    PHONE, EMAIL, NONE;

    companion object {
        @JsonCreator
        @JvmStatic
        fun fromValue(value: String): ContactType {
            return when (value.lowercase()) {
                "phone" -> PHONE
                "email" -> EMAIL
                else -> NONE
            }
        }
    }

    @JsonValue
    fun toValue(): String {
        return this.name.lowercase()
    }
}
