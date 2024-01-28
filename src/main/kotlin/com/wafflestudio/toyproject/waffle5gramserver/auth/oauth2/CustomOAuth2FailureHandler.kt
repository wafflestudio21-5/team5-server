package com.wafflestudio.toyproject.waffle5gramserver.auth.oauth2

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.AuthenticationException
import org.springframework.security.oauth2.core.OAuth2AuthenticationException
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler
import org.springframework.stereotype.Component

@Component
class CustomOAuth2FailureHandler : SimpleUrlAuthenticationFailureHandler() {

    companion object {
        private const val FAILURE_REDIRECT_URI = "https://www.waffle5gram.com/"
    }

    override fun onAuthenticationFailure(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        exception: AuthenticationException?
    ) {
        if (exception is OAuth2AuthenticationException && exception.error.errorCode == "facebook") {
            redirectStrategy.sendRedirect(request, response, "$FAILURE_REDIRECT_URI?result=failure")
            return
        }
        println("super.onAuthenticationFailure")
        super.onAuthenticationFailure(request, response, exception)
    }
}
