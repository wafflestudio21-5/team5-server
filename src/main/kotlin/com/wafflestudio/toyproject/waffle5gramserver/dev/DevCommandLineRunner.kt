package com.wafflestudio.toyproject.waffle5gramserver.dev

import com.wafflestudio.toyproject.waffle5gramserver.user.repository.UserEntity
import com.wafflestudio.toyproject.waffle5gramserver.user.repository.UserRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.support.TransactionTemplate
import java.util.Date

@Component
@Profile("dev")
class DevCommandLineRunner(
    private val userRepository: UserRepository,
    private val txManager: PlatformTransactionManager
) : CommandLineRunner {

    private val txTemplate = TransactionTemplate(txManager)

    override fun run(vararg args: String?) {
        for (i in 0..2) {
            userRepository.save(
                UserEntity(
                    username = "Test user name $i",
                    name = "Test name $i",
                    password = "12345$i",
                    birthday = Date(),
                    bio = "My bio $i",
                    isPrivate = false,
                    profileImageUrl = null,
                    pronoun = null
                )
            )
            Thread.sleep(2000)
        }
        txTemplate.execute {
            // JPA auditing is not working
            // https://stackoverflow.com/questions/56823730/spring-data-jpa-auditing-not-working-for-the-jparepository-update-method-with-m
            // userRepository.updateNameById(2, "Modified name")

            // JPA auditing is working
            userRepository.findById(2).get().name = "Modified name"
        }
    }
}
