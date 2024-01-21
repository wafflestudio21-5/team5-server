package com.wafflestudio.toyproject.waffle5gramserver.utils

import com.amazonaws.auth.AWSStaticCredentialsProvider
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.services.s3.AmazonS3Client
import com.amazonaws.services.s3.AmazonS3ClientBuilder
import com.wafflestudio.toyproject.waffle5gramserver.properties.S3properties
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationPropertiesScan
class S3Config(
    s3properties: S3properties
) {
    private val region: String = s3properties.region.static
    private val accessKey: String = s3properties.credentials.accessKey
    private val secretKey: String = s3properties.credentials.secretKey

    @Bean
    fun amazonS3Client(): AmazonS3Client {
        val basicAWSCredentials: BasicAWSCredentials = BasicAWSCredentials(accessKey, secretKey)
        return AmazonS3ClientBuilder
            .standard()
            .withRegion(region)
            .withCredentials(AWSStaticCredentialsProvider(basicAWSCredentials))
            .build()
            as AmazonS3Client
    }
}
