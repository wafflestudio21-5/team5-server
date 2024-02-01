package com.wafflestudio.toyproject.waffle5gramserver.post.mapper

import com.wafflestudio.toyproject.waffle5gramserver.explore.dto.ExplorePreview
import com.wafflestudio.toyproject.waffle5gramserver.post.repository.PostDetailQueryResult
import com.wafflestudio.toyproject.waffle5gramserver.post.repository.PostEntity
import com.wafflestudio.toyproject.waffle5gramserver.post.repository.PostMediaEntity
import com.wafflestudio.toyproject.waffle5gramserver.post.service.PostAuthor
import com.wafflestudio.toyproject.waffle5gramserver.post.service.PostBrief
import com.wafflestudio.toyproject.waffle5gramserver.post.service.PostDetail
import com.wafflestudio.toyproject.waffle5gramserver.post.service.PostMedia
import com.wafflestudio.toyproject.waffle5gramserver.post.service.PostMediasBrief

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
                category = entity.category
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

        fun toPostMediasBrief(entity: PostEntity, commentCount: Long? = null): PostMediasBrief {
            return PostMediasBrief(
                id = entity.id,
                createdAt = entity.createdAt,
                medias = entity.medias.map { toPostMediaDTO(it) },
                category = entity.category,
                likeCount = entity.likeCount,
                commentCount = commentCount
            )
        }

        fun toPostMediaDetail(postDetailQueryResult: PostDetailQueryResult): PostDetail {
            val post = postDetailQueryResult.getPostEntity()
            val profileImageUrl = postDetailQueryResult.getProfileImageUrl()
                ?: "https://upload.wikimedia.org/wikipedia/commons/thumb/2/2c/Default_pfp.svg/680px-Default_pfp.svg.png?20220226140232"
            return PostDetail(
                id = post.id,
                author = PostAuthor(
                    id = postDetailQueryResult.getUserId(),
                    username = postDetailQueryResult.getUsername(),
                    profileImageUrl = profileImageUrl
                ),
                content = post.content,
                media = post.medias.map { toPostMediaDTO(it) },
                liked = postDetailQueryResult.getPostLikeCount() >= 1,
                likeCount = post.likeCount,
                saved = postDetailQueryResult.getPostSaveCount() >= 1,
                commentCount = postDetailQueryResult.getCommentCount().toInt(),
                hideLike = !post.likeCountDisplayed,
                createdAt = post.createdAt,
                category = post.category
            )
        }

        fun toExplorePreview(entity: PostEntity): ExplorePreview {
            return ExplorePreview(
                postId = entity.id,
                thumbnailUrl = entity.medias.getOrNull(0)?.mediaUrl
            )
        }
    }
}
