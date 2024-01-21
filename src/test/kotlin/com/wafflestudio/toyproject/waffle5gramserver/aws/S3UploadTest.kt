package com.wafflestudio.toyproject.waffle5gramserver.aws;

import com.amazonaws.services.s3.AmazonS3Client
import com.wafflestudio.toyproject.waffle5gramserver.utils.S3ImageUpload
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile
import org.springframework.web.multipart.MultipartFile
import java.nio.file.Paths

@SpringBootTest
class S3UploadTest @Autowired constructor(
    private val amazonS3Client: AmazonS3Client
) {
    private fun createImage(): MultipartFile {
        val filePath = Paths.get("src/test/resources/spring.jpg")
        val originalFilename = "spring.jpg"
        val contentType = "image/png"
        val content = filePath.toFile().readBytes()
        return MockMultipartFile(
            originalFilename,
            originalFilename,
            contentType,
            content
        )
    }

    @Test
    fun `이미지 업로드`() {
        // Given
        val image = createImage()

        // When
        val uploadedUrl = S3ImageUpload(amazonS3Client).uploadImage(image)

        // Then
        assertNotNull(uploadedUrl)
    }

    @Test
    fun `이미지 삭제`() {
        // Given
        val image = createImage()
        val uploadedUrl = S3ImageUpload(amazonS3Client).uploadImage(image)

        // When
        val deletedImage = S3ImageUpload(amazonS3Client).deleteImage(uploadedUrl)

        // Then
        assertEquals(deletedImage, image.originalFilename)
    }
}
