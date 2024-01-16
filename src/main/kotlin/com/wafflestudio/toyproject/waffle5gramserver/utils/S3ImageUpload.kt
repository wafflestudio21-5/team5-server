package com.wafflestudio.toyproject.waffle5gramserver.utils

import com.amazonaws.services.s3.AmazonS3Client
import com.amazonaws.services.s3.model.ObjectMetadata
import org.springframework.web.multipart.MultipartFile

class S3ImageUpload(
    private val amazonS3Client: AmazonS3Client,
    private val bucket: String
) {
    fun uploadImage(image: MultipartFile): String {
        val fileName = image.originalFilename
        val uploadFileName = System.currentTimeMillis().toString() + "_" + fileName
        val metadata  = ObjectMetadata()
        metadata.contentType = image.contentType
        metadata.contentLength = image.size

        return try {
            amazonS3Client.putObject(bucket, uploadFileName, image.inputStream, metadata)
            val uploadFileURL = amazonS3Client.getUrl(bucket, uploadFileName)
            uploadFileURL.toString()
        } catch (e: Exception) {
            "upload error"
        }
    }
}