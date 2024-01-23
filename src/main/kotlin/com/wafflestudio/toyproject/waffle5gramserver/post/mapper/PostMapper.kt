package com.wafflestudio.toyproject.waffle5gramserver.post.mapper

import com.wafflestudio.toyproject.waffle5gramserver.post.repository.PostEntity
import com.wafflestudio.toyproject.waffle5gramserver.post.repository.PostMediaEntity
import com.wafflestudio.toyproject.waffle5gramserver.post.service.PostAuthor
import com.wafflestudio.toyproject.waffle5gramserver.post.service.PostBrief
import com.wafflestudio.toyproject.waffle5gramserver.post.service.PostDetail
import com.wafflestudio.toyproject.waffle5gramserver.post.service.PostMedia

class PostMapper {
    companion object {
        fun toPostBriefDTO(entity: PostEntity): PostBrief {
            return PostBrief(
                id = entity.id,
                content = entity.content,
                createdAt = entity.createdAt,
            )
        }

        fun toPostDetailDTO(
            entity: PostEntity,
            isLiked: Boolean,
            isSaved: Boolean,
        ): PostDetail {
            return PostDetail(
                id = entity.id,
                author =
                PostAuthor(
                    id = entity.user.id,
                    username = entity.user.username,
                    profileImageUrl = entity.user.profileImageUrl ?: "",
                ),
                content = entity.content,
                media = entity.medias.map { media -> toPostMediaDTO(media) },
                liked = isLiked,
                likeCount = entity.likeCount,
                saved = isSaved,
                commentCount = entity.comments.size,
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
            )
        }
    }
}
