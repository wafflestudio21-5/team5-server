package com.wafflestudio.toyproject.waffle5gramserver

import com.wafflestudio.toyproject.waffle5gramserver.properties.S3properties
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@ConfigurationPropertiesScan
@EnableJpaAuditing
@SpringBootApplication
class Waffle5gramserverApplication

fun main(args: Array<String>) {
    runApplication<Waffle5gramserverApplication>(*args)
}
