package com.wafflestudio.toyproject.waffle5gramserver.utils

import com.amazonaws.services.s3.AmazonS3Client
import com.amazonaws.services.s3.model.ObjectMetadata
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile
import java.util.UUID

@Service
class S3ImageUpload(
    private val amazonS3Client: AmazonS3Client
) {
    private final val bucket: String = "waffle5grambucket"

    @Transactional
    fun uploadImage(image: MultipartFile): String {
        val fileName = UUID.randomUUID().toString() + "-" + image.originalFilename
        val metadata = ObjectMetadata()
        metadata.contentType = image.contentType
        metadata.contentLength = image.size

        amazonS3Client.putObject(bucket, fileName, image.inputStream, metadata)
        val uploadFileURL = amazonS3Client.getUrl(bucket, fileName)
        return uploadFileURL.toString()
    }

    fun deleteImage(imageUrl: String): String {
        val fileName = imageUrl.split("/").last()
        amazonS3Client.deleteObject(bucket, fileName)
        return fileName
    }
}
