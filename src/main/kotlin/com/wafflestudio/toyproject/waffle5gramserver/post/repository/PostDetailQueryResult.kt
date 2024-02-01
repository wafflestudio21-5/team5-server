package com.wafflestudio.toyproject.waffle5gramserver.post.repository

interface PostDetailQueryResult {
    fun getPostEntity(): PostEntity
    fun getUserId(): Long
    fun getUsername(): String
    fun getProfileImageUrl(): String?
    fun getPostLikeCount(): Int
    fun getPostSaveCount(): Int
    fun getCommentCount(): Long
}
