package com.wafflestudio.toyproject.waffle5gramserver.properties

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "jwt")
data class JWTProperties(
    val accessTokenSecretKey: String,
    val refreshTokenSecretKey: String,
    val ttlMinutesAccessToken: Long,
    val ttlMinutesRefreshToken: Long,
    val refreshTokenCookieSecure: Boolean
)
