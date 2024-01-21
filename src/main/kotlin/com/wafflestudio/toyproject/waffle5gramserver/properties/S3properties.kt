package com.wafflestudio.toyproject.waffle5gramserver.properties

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "cloud.aws")
data class S3properties (
    val region: Region,
    val credentials: Credentials
)

class Region (
    val static: String
)

class Credentials (
    val accessKey: String,
    val secretKey: String
)