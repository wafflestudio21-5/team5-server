package com.wafflestudio.toyproject.waffle5gramserver.post.service

import com.wafflestudio.toyproject.waffle5gramserver.utils.S3Exception
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
    txManager: PlatformTransactionManager
) : PostImageService {
    private val threads = Executors.newFixedThreadPool(10)
    private val txTemplate = TransactionTemplate(txManager)

    override fun uploadImages(images: List<MultipartFile>): List<String> {
        val latch = CountDownLatch(images.size)
        val imageUrls = mutableListOf<Map<Long, String>>()

        images.forEachIndexed { index, multipartFile ->
            threads.submit {
                try {
                    txTemplate.execute {
                        val imageUrl = s3ImageUpload.uploadImage(multipartFile)
                        imageUrls.add(mapOf(index.toLong() to imageUrl))
                    }
                } catch (e: Exception) {
                    throw S3Exception("S3 이미지 업로드에 실패했습니다.")
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
        // 비동기 처리
        imageUrls.forEach {
            s3ImageUpload.deleteImage(it)
        }
    }
}
