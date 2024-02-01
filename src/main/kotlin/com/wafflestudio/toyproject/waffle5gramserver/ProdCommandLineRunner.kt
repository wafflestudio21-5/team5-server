package com.wafflestudio.toyproject.waffle5gramserver

import com.wafflestudio.toyproject.waffle5gramserver.comment.repository.CommentEntity
import com.wafflestudio.toyproject.waffle5gramserver.comment.service.CommentService
import com.wafflestudio.toyproject.waffle5gramserver.follow.repository.FollowEntity
import com.wafflestudio.toyproject.waffle5gramserver.follow.repository.FollowRepository
import com.wafflestudio.toyproject.waffle5gramserver.post.repository.PostCategory
import com.wafflestudio.toyproject.waffle5gramserver.post.repository.PostEntity
import com.wafflestudio.toyproject.waffle5gramserver.post.repository.PostMediaEntity
import com.wafflestudio.toyproject.waffle5gramserver.post.repository.PostRepository
import com.wafflestudio.toyproject.waffle5gramserver.post.service.PostLikeService
import com.wafflestudio.toyproject.waffle5gramserver.post.service.PostSaveService
import com.wafflestudio.toyproject.waffle5gramserver.user.repository.ContactEntity
import com.wafflestudio.toyproject.waffle5gramserver.user.repository.ContactType
import com.wafflestudio.toyproject.waffle5gramserver.user.repository.UserEntity
import com.wafflestudio.toyproject.waffle5gramserver.user.repository.UserRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Profile
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.support.TransactionTemplate
import java.util.Date
import kotlin.random.Random

@Component
@Profile("prod", "dev-secure")
class ProdCommandLineRunner(
    private val userRepository: UserRepository,
    private val postRepository: PostRepository,
    private val passwordEncoder: PasswordEncoder,
    private val txManager: PlatformTransactionManager,
    private val postLikeService: PostLikeService,
    private val commentService: CommentService,
    private val followRepository: FollowRepository,
    private val postSaveService: PostSaveService,
) : CommandLineRunner {

    private val txTemplate = TransactionTemplate(txManager)

    override fun run(vararg args: String?) {
        txTemplate.execute {
            val userIdList = mutableListOf<Long>()
            for (i in 0..20) {
                val user = userRepository.save(
                    UserEntity(
                        username = "user-$i",
                        name = "Test name $i",
                        password = passwordEncoder.encode("password-$i"),
                        birthday = Date(),
                        bio = "My bio $i",
                        gender = "female",
                    )
                )
                userIdList.add(user.id)
                val randomStart = Random.nextInt(0, i + 1)
                val randomStep = Random.nextInt(1, minOf(i + 2, 5))
                for (j in randomStart until i step randomStep) {
                    followRepository.save(
                        FollowEntity(
                            follower = userRepository.findByUsername("user-$j").get(),
                            followee = user
                        )
                    )
                }
            }
            val users = listOf(
                UserEntity(
                    username = "kim_m",
                    name = "김민수",
                    password = passwordEncoder.encode("kim_m"),
                    birthday = Date(360000L * 48000L),
                    gender = "남",
                    bio = "안녕하세요 그냥 테스트 사용자일 뿐인 김민수입니다"
                ),
                UserEntity(
                    username = "lee_zi",
                    name = "이지은",
                    password = passwordEncoder.encode("lee_zi"),
                    birthday = Date(360000L * 96000L),
                    gender = "여",
                    profileImageUrl = "https://waffle5grambucket.s3.ap-northeast-2.amazonaws.com/f7e0eea1-4ace-4690-a4e2-26c03d201895-moon.jpg",
                    bio = "안녕하세요 :) Hello everybody"
                ),
                UserEntity(
                    username = "django_official",
                    name = "Django Framework",
                    password = passwordEncoder.encode("django_official"),
                    birthday = Date(360000L * 70000L),
                    profileImageUrl = "https://waffle5grambucket.s3.ap-northeast-2.amazonaws.com/503c6347-fe62-453d-9d34-8a444e63047d-django.png",
                    bio = "Django is an open source server framework based on Python language."
                ),
                UserEntity(
                    username = "traveler555",
                    name = "John",
                    password = passwordEncoder.encode("traveler555"),
                    birthday = Date(360000L * 80000L),
                    bio = "여행 전문 계정"
                )
            )
            users.forEach {
                it.contacts.add(
                    ContactEntity(it, ContactType.EMAIL, "${it.username}@waffle.com", true)
                )
                userRepository.save(it)
                userIdList.add(it.id)
            }
            val posts = mapOf(
                "초승달" to "https://waffle5grambucket.s3.ap-northeast-2.amazonaws.com/f7e0eea1-4ace-4690-a4e2-26c03d201895-moon.jpg",
                "rainy day" to "https://waffle5grambucket.s3.ap-northeast-2.amazonaws.com/e41ff6bf-e125-4d0d-89d0-9fcba53c42b4-raindrop.jpg",
                "새들이 날아가는 아름다운 하늘 사진" to "https://waffle5grambucket.s3.ap-northeast-2.amazonaws.com/595854f3-fa73-4f2e-9034-30c1dd0cbc6f-birds.jpg"
            )
            for (i in users.indices) {
                val userEntity = users[i]
                posts.forEach {
                    val post = PostEntity(
                        content = it.key,
                        user = userEntity,
                        category = PostCategory.LIFE
                    )
                    post.medias.add(
                        PostMediaEntity(
                            mediaUrl = it.value,
                            mediaOrder = 0,
                            post = post
                        )
                    )
                    post.comments.add(
                        CommentEntity(
                            text = "${it.key}... 정말 좋은 사진이네요",
                            user = users[(i + 1).mod(users.size)],
                            post = post
                        )
                    )
                    postRepository.save(post)
                }
            }
            postRepository.save(
                PostEntity(
                    content = "Our new partners: nginx, react",
                    user = users[2],
                    category = PostCategory.LIFE
                ).apply {
                    medias.add(
                        PostMediaEntity(
                            mediaUrl = "https://waffle5grambucket.s3.ap-northeast-2.amazonaws.com/e7231e28-34c9-43ee-8023-1d432d7daf12-nginx.png",
                            mediaOrder = 0,
                            post = this
                        )
                    )
                    medias.add(
                        PostMediaEntity(
                            mediaUrl = "https://waffle5grambucket.s3.ap-northeast-2.amazonaws.com/019ca024-efdf-44b8-ac68-b2368702880c-react.png",
                            mediaOrder = 1,
                            post = this
                        )
                    )
                    comments.add(
                        CommentEntity(
                            text = "기대하던 소식입니다 Good",
                            user = users[0],
                            post = this
                        )
                    )
                }
            )
            val travelPosts = mapOf(
                "Paris" to "https://waffle5grambucket.s3.ap-northeast-2.amazonaws.com/1f056fd0-4f45-46bf-8826-4ea28ae16b10-paris1-travel.jpg",
                "Barcelona Familia" to "https://waffle5grambucket.s3.ap-northeast-2.amazonaws.com/81b3edad-5768-4602-a01f-c8447df75140-barcelona1-travel.jpeg",
                "Barcelona Guel" to "https://waffle5grambucket.s3.ap-northeast-2.amazonaws.com/81b3edad-5768-4602-a01f-c8447df75140-barca2-travel.jpeg",
                "Vietnam" to "https://waffle5grambucket.s3.ap-northeast-2.amazonaws.com/8b159e29-1f51-42e0-b411-1c62e49250f5-vietnam-travel.jpg",
                "Louvre Museum" to "https://waffle5grambucket.s3.ap-northeast-2.amazonaws.com/b332ed5c-9a49-4388-b22a-0e54a819cb10-paris3-travel.jpeg"
            )
            val postIdList = mutableListOf<Long>()
            travelPosts.forEach {
                val post = postRepository.save(
                    PostEntity(
                        content = it.key,
                        user = users[3],
                        category = PostCategory.TRAVEL
                    ).apply {
                        medias.add(
                            PostMediaEntity(
                                mediaUrl = it.value,
                                mediaOrder = 1,
                                post = this
                            )
                        )
                    }
                )
                postIdList.add(post.id)
            }
            val japanTravelPostUrls = listOf(
                "https://waffle5grambucket.s3.ap-northeast-2.amazonaws.com/a1ed3d06-d528-4494-b6e9-24f47bba8ab9-japan1-travel.jpg",
                "https://waffle5grambucket.s3.ap-northeast-2.amazonaws.com/571797a7-8413-4007-94a4-e4e4b34c01a1-japan-travel2.jpg",
                "https://waffle5grambucket.s3.ap-northeast-2.amazonaws.com/d407af09-fb24-4e79-9960-88ca365b7de6-japan3-travel.jpg",
            )
            postRepository.save(
                PostEntity(
                    content = "japan travel list",
                    user = users[3],
                    category = PostCategory.TRAVEL
                ).apply {
                    japanTravelPostUrls.forEachIndexed { index, s ->
                        medias.add(
                            PostMediaEntity(
                                mediaUrl = s,
                                mediaOrder = index + 1,
                                post = this
                            )
                        )
                    }
                }
            )
            for (i in 0 until minOf(userIdList.size, postIdList.size)) {
                for (j in 0..i) {
                    postLikeService.create(postIdList[i], userIdList[j])
                    commentService.create(postIdList[i], "Good place", userIdList[j])
                }
            }
            for (i in 1 until 7 step 2) {
                postLikeService.create(i.toLong(), userIdList[0])
                postSaveService.create(i.toLong() + 1, userIdList[0])
            }
        }
    }
}
