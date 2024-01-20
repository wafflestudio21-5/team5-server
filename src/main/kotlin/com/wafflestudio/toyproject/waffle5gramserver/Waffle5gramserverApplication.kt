package com.wafflestudio.toyproject.waffle5gramserver

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@EnableJpaAuditing
@SpringBootApplication
@EnableJpaAuditing
class Waffle5gramserverApplication

fun main(args: Array<String>) {
    runApplication<Waffle5gramserverApplication>(*args)
}
