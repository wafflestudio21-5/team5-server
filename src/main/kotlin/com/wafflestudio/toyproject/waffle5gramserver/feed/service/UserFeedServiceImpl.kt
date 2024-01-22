package com.wafflestudio.toyproject.waffle5gramserver.feed.service

import com.wafflestudio.toyproject.waffle5gramserver.post.mapper.PostMapper
import com.wafflestudio.toyproject.waffle5gramserver.post.repository.PostRepository
import com.wafflestudio.toyproject.waffle5gramserver.post.service.PostDetail
import com.wafflestudio.toyproject.waffle5gramserver.post.service.PostNotFoundException
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service

@Service
class UserFeedServiceImpl(
    private val postRepository: PostRepository,
) : UserFeedService {
    override fun getUserFeedPreview(
        userId: Long,
        cursor: Long?,
        limit: Int,
    ): List<PostPreview> {
        val pageable = PageRequest.of(0, limit)
        val posts =
            if (cursor == null) {
                postRepository.findLatestPostsByUserId(userId, pageable)
            } else {
                postRepository.findPostsByUserIdAndCursor(userId, cursor, pageable)
            }

        return posts.map { post ->
            PostPreview(
                id = post.id,
                thumbnailUrl = post.medias.firstOrNull()?.mediaUrl ?: "",
                // 첫 번째 미디어의 URL을 사용
            )
        }
    }

    override fun getPostDetails(postId: Long): PostDetail {
        val post = postRepository.findById(postId).orElseThrow { PostNotFoundException() }
        return PostMapper.toPostDetailDTO(post)
    }

    override fun loadNewerPosts(
        userId: Long,
        cursor: Long?,
        limit: Int,
    ): List<PostDetail> {
        val pageable = PageRequest.of(0, limit)
        val posts = postRepository.findNewerPostsByUserIdAndCursor(userId, cursor, pageable)
        return posts.map { post -> PostMapper.toPostDetailDTO(post) }
    }

    override fun loadOlderPosts(
        userId: Long,
        cursor: Long?,
        limit: Int,
    ): List<PostDetail> {
        val pageable = PageRequest.of(0, limit)
        val posts = postRepository.findOlderPostsByUserIdAndCursor(userId, cursor, pageable)
        return posts.map { post -> PostMapper.toPostDetailDTO(post) }
    }
}
