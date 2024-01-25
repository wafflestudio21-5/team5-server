package com.wafflestudio.toyproject.waffle5gramserver.auth.oauth2

import com.wafflestudio.toyproject.waffle5gramserver.auth.jwt.JwtUtils
import jakarta.servlet.http.Cookie
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler
import org.springframework.stereotype.Component

@Component
class CustomOAuth2SuccessHandler(
    private val jwtUtils: JwtUtils
) : SimpleUrlAuthenticationSuccessHandler() {

    companion object {
        private const val SUCCESS_REDIRECT_URI = "https://www.waffle5gram.com/"
        private const val REGISTER_REDIRECT_URI = "https://www.waffle5gram.com/signUp/username"
    }

    override fun onAuthenticationSuccess(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        authentication: Authentication?
    ) {
        val oAuth2User = authentication?.principal as CustomOAuth2User
        val refreshToken = jwtUtils.generateRefreshToken(oAuth2User.username)
        if (oAuth2User.isExistingUser) {
            addRefreshToken(response, refreshToken, "/api/v1/auth/refresh_token")
            val uri = addQueryParameter(SUCCESS_REDIRECT_URI, "success")
            redirectStrategy.sendRedirect(request, response, uri)
        } else {
            addRefreshToken(response, refreshToken, "/api/v1/auth/facebook_signup")
            val uri = addQueryParameter(REGISTER_REDIRECT_URI, "register")
            redirectStrategy.sendRedirect(request, response, uri)
        }
    }

    private fun addRefreshToken(
        response: HttpServletResponse?,
        refreshToken: String,
        cookiePath: String
    ) {
        val cookie = Cookie("refresh_token", refreshToken).apply {
            isHttpOnly = true
            path = cookiePath
            setAttribute("SameSite", "Strict")
        }
        response?.addCookie(cookie)
    }

    private fun addQueryParameter(
        baseUri: String,
        resultValue: String
    ): String {
        return "$baseUri?result=$resultValue"
    }
}
