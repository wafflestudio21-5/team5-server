package com.wafflestudio.toyproject.waffle5gramserver.post.service

sealed class PostException : RuntimeException()

class PostNotFoundException : PostException()

class PostNotAuthorizedException : PostException()

class PostAlreadyLikedException : PostException()

class PostNotLikedException : PostException()

class UserNotFoundException : PostException()
