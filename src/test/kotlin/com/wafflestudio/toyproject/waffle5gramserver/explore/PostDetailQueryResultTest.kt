package com.wafflestudio.toyproject.waffle5gramserver.explore

import com.wafflestudio.toyproject.waffle5gramserver.post.repository.PostRepository
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
// import org.springframework.data.domain.PageRequest
import org.springframework.transaction.annotation.Transactional

@Transactional
@SpringBootTest
class PostDetailQueryResultTest @Autowired constructor(
    private val postRepository: PostRepository
) {
    @Test
    fun getPostDetailQueryResult() {
        // val x = postRepository.findSlicedPostDetails(
        //     PageRequest.of(0, 18),
        //     currentUserId = 1
        // )
        // val y = x.content
        // for (r in y) {
        //     val p = r.getPostEntity()
        //     println(p.id)
        //     println(p.content)
        //     println(p.category)
        //     println(r.getUserId())
        //     println(r.getUsername())
        //     println(r.getProfileImageUrl())
        //     println(r.getPostLikeCount())
        //     println(r.getPostSaveCount())
        //     println(r.getCommentCount())
        // }
    }
}
