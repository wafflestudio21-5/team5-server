package com.wafflestudio.toyproject.waffle5gramserver.auth.oauth2

import com.wafflestudio.toyproject.waffle5gramserver.auth.jwt.JwtUtils
import com.wafflestudio.toyproject.waffle5gramserver.auth.service.TokenRefreshService
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.Authentication
import org.springframework.security.oauth2.core.OAuth2AuthenticationException
import org.springframework.security.oauth2.core.OAuth2Error
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler
import org.springframework.stereotype.Component

@Component
class CustomOAuth2SuccessHandler(
    private val jwtUtils: JwtUtils,
    private val tokenRefreshService: TokenRefreshService
) : SimpleUrlAuthenticationSuccessHandler() {

    companion object {
        private const val SUCCESS_REDIRECT_URI = "https://www.waffle5gram.com/"
    }

    override fun onAuthenticationSuccess(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        authentication: Authentication?
    ) {
        if (response == null) {
            throw OAuth2AuthenticationException(
                OAuth2Error("facebook"),
                "CustomOAuth2SuccessHandler response not found"
            )
        }
        val oAuth2User = authentication?.principal as CustomOAuth2User
        val refreshToken = jwtUtils.generateRefreshToken(oAuth2User.username)
        if (oAuth2User.isExistingUser) {
            tokenRefreshService.addRefreshTokenCookie(response, refreshToken, "/api/v1/auth/refresh_token")
            val uri = addQueryParameter(SUCCESS_REDIRECT_URI, "success")
            redirectStrategy.sendRedirect(request, response, uri)
        } else {
            tokenRefreshService.addRefreshTokenCookie(response, refreshToken, "/api/v1/auth/facebook_signup")
            val uri = addQueryParameter(SUCCESS_REDIRECT_URI, "register")
            redirectStrategy.sendRedirect(request, response, uri)
        }
    }

    private fun addQueryParameter(
        baseUri: String,
        resultValue: String
    ): String {
        return "$baseUri?result=$resultValue"
    }
}
