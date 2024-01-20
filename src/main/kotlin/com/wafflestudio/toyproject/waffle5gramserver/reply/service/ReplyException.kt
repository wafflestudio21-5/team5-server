package com.wafflestudio.toyproject.waffle5gramserver.reply.service

sealed class ReplyException : RuntimeException()

class ReplyNotAuthorizedException : ReplyException()

class ReplyNotFoundException : ReplyException()

class UserNotFoundException : ReplyException()

class ReplyAlreadyLikedException : ReplyException()

class ReplyNotLikedException : ReplyException()
