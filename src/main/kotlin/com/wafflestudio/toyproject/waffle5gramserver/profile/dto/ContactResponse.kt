package com.wafflestudio.toyproject.waffle5gramserver.profile.dto

data class ContactResponse(
    val contactType: String,
    val contactValue: String,
    val isConfirmed: Boolean,
)
