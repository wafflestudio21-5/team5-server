package com.wafflestudio.toyproject.waffle5gramserver.utils

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType

@Configuration
class ImageTypeConfig {

    @Bean
    fun allowedImageType(): List<String> {
        return listOf(
            MediaType.IMAGE_JPEG_VALUE,
            MediaType.IMAGE_PNG_VALUE,
            MediaType.IMAGE_GIF_VALUE,
        )
    }
}
