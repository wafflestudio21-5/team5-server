package com.wafflestudio.toyproject.waffle5gramserver.post.mapper

import com.wafflestudio.toyproject.waffle5gramserver.post.repository.PostEntity
import com.wafflestudio.toyproject.waffle5gramserver.post.repository.PostMediaEntity
import com.wafflestudio.toyproject.waffle5gramserver.post.service.PostBrief
import com.wafflestudio.toyproject.waffle5gramserver.post.service.PostDetail
import com.wafflestudio.toyproject.waffle5gramserver.post.service.PostMedia

class PostMapper {
    companion object {
        fun toPostBriefDTO(entity: PostEntity): PostBrief {
            return PostBrief(
                id = entity.id,
                content = entity.content,
                createdAt = entity.createdAt
            )
        }

        fun toPostDetailDTO(entity: PostEntity, liked: Boolean): PostDetail {
            return PostDetail(
                id = entity.id,
                userId = entity.user.id,
                content = entity.content,
                media = entity.medias.map { media -> toPostMediaDTO(media) },
                liked = liked,
                likeCount = 0,
                commentCount = entity.comments.size,
                disableComment = entity.commentDisplayed,
                hideLike = entity.likeCountDisplayed,
                createdAt = entity.createdAt,
            )
        }

        private fun toPostMediaDTO(media: PostMediaEntity): PostMedia {
            return PostMedia(
                id = media.id,
                postId = media.post.id,
                order = media.mediaOrder,
                url = media.mediaUrl,
                mediaType = media.mediaType,
                createdAt = media.createdAt,
            )
        }
    }
}
