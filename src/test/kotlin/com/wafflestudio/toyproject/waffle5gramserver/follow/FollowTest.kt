package com.wafflestudio.toyproject.waffle5gramserver.follow

import com.wafflestudio.toyproject.waffle5gramserver.follow.repository.FollowRepository
import com.wafflestudio.toyproject.waffle5gramserver.follow.repository.FollowRequestRepository
import com.wafflestudio.toyproject.waffle5gramserver.follow.service.FollowService
import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@Transactional
@SpringBootTest
class FollowTest
@Autowired
constructor(
    private val followService: FollowService,
    private val followRepository: FollowRepository,
    private val followRequestRepository: FollowRequestRepository,
)
