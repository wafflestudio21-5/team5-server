package com.wafflestudio.toyproject.waffle5gramserver.post.service

import org.springframework.web.multipart.MultipartFile

interface PostImageService {
    fun uploadImages(
        images: List<MultipartFile>
    ): List<String>
}