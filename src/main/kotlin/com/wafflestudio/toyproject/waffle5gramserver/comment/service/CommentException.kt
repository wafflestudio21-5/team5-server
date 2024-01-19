package com.wafflestudio.toyproject.waffle5gramserver.comment.service

sealed class CommentException : RuntimeException()

class CommentNotFoundException : CommentException()

class CommentNotAuthorizedException : CommentException()

class UserNotFoundException : CommentException()

class CommentAlreadyLikedException : CommentException()

class CommentNotLikedException : CommentException()
