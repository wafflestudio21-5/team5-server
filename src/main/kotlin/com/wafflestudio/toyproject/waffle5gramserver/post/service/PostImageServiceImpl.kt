package com.wafflestudio.toyproject.waffle5gramserver.post.service

import com.wafflestudio.toyproject.waffle5gramserver.global.error_handling.ErrorCode
import com.wafflestudio.toyproject.waffle5gramserver.post.exception.PostImageException
import com.wafflestudio.toyproject.waffle5gramserver.utils.S3ImageUpload
import org.springframework.stereotype.Service
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.support.TransactionTemplate
import org.springframework.web.multipart.MultipartFile
import java.util.concurrent.CountDownLatch
import java.util.concurrent.Executors

@Service
class PostImageServiceImpl(
    private val s3ImageUpload: S3ImageUpload,
    private val allowedImageTypes: List<String>,
    txManager: PlatformTransactionManager
) : PostImageService {
    private val threads = Executors.newFixedThreadPool(10)
    private val txTemplate = TransactionTemplate(txManager)

    override fun uploadImages(images: List<MultipartFile>): List<String> {
        val latch = CountDownLatch(images.size)
        val imageUrls = mutableListOf<Map<Long, String>>()

        images.forEachIndexed { index, multipartFile ->
            val contentType = multipartFile.contentType ?: throw PostImageException(ErrorCode.FILE_CONVERT_FAIL)
            if (!allowedImageTypes.contains(contentType)) {
                throw PostImageException(ErrorCode.INVALID_IMAGE_TYPE)
            }

            threads.submit {
                try {
                    txTemplate.execute {
                        val imageUrl = s3ImageUpload.uploadImage(multipartFile)
                        imageUrls.add(mapOf(index.toLong() to imageUrl))
                    }
                } catch (e: Exception) {
                    throw PostImageException(ErrorCode.S3_UPLOAD_ERROR)
                } finally {
                    latch.countDown()
                }
            }
        }
        latch.await()
        threads.shutdown()

        return imageUrls.sortedBy { it.keys.first() }.map { it.values.first() }
    }

    override fun deleteImages(imageUrls: List<String>) {
        val latch = CountDownLatch(imageUrls.size)

        imageUrls.forEach { imageUrl ->
            threads.submit {
                try {
                    s3ImageUpload.deleteImage(imageUrl)
                } catch (e: Exception) {
                    throw PostImageException(ErrorCode.S3_DELETE_ERROR)
                } finally {
                    latch.countDown()
                }
            }
        }
        latch.await()
        threads.shutdown()
    }
}
