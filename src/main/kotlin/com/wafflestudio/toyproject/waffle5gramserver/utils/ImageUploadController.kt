package com.wafflestudio.toyproject.waffle5gramserver.utils

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/api/v1")
class ImageUploadController (
    private val s3ImageUpload: S3ImageUpload,
) {

    @Transactional
    @PostMapping("/images")
    fun postImage(
        @RequestParam("images") images : List<MultipartFile>
    ): ResponseEntity<List<String>> {
        val imageUrls = images.map { image ->
            s3ImageUpload.uploadImage(image)
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(imageUrls)
    }
}