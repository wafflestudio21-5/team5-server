package com.wafflestudio.toyproject.waffle5gramserver.dev

import com.wafflestudio.toyproject.waffle5gramserver.user.repository.UserEntity
import com.wafflestudio.toyproject.waffle5gramserver.user.repository.UserRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Profile
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.support.TransactionTemplate
import java.util.Date

@Component
@Profile("dev")
class DevCommandLineRunner(
    private val userRepository: UserRepository,
    private val txManager: PlatformTransactionManager,
    private val passwordEncoder: PasswordEncoder
) : CommandLineRunner {

    private val txTemplate = TransactionTemplate(txManager)

    override fun run(vararg args: String?) {

        for (i in 0..2) {
            userRepository.save(
                UserEntity(
                    username = "user-$i",
                    name = "Test name $i",
                    password = passwordEncoder.encode("password-$i"),
                    birthday = Date(),
                    bio = "My bio $i",
                    isPrivate = false,
                    profileImageUrl = null,
                    gender = "male",
                    isCustomGender = false,
                )
            )
        }

        txTemplate.execute {
            // JPA auditing is not working
            // https://stackoverflow.com/questions/56823730/spring-data-jpa-auditing-not-working-for-the-jparepository-update-method-with-m
            userRepository.updateNameById(2, "Modified name of id 2")
            userRepository.flush()

            // JPA auditing is working
            userRepository.findById(3).get().name = "Modified name of id 3"
        }
    }
}
