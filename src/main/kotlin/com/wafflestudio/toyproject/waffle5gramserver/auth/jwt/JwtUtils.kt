package com.wafflestudio.toyproject.waffle5gramserver.auth.jwt

import com.wafflestudio.toyproject.waffle5gramserver.properties.JWTProperties
import io.jsonwebtoken.JwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.HttpHeaders
import org.springframework.stereotype.Component
import java.util.Date
import javax.crypto.SecretKey

@Component
class JwtUtils(
    private val jwtProperties: JWTProperties
) {

    private val accessTokenSecretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtProperties.accessTokenSecretKey))
    private val refreshTokenSecretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtProperties.refreshTokenSecretKey))

    private fun generateToken(username: String, secretKey: SecretKey, ttlInMinutes: Long): String {
        val expiredTime = Date(System.currentTimeMillis() + ttlInMinutes * 60 * 1000)
        return Jwts.builder().subject(username).expiration(expiredTime).signWith(secretKey).compact()
    }

    fun generateAccessToken(username: String): String {
        return generateToken(username, accessTokenSecretKey, 5) // 5 minutes
    }

    fun generateRefreshToken(username: String): String {
        return generateToken(username, refreshTokenSecretKey, 6 * 60) // 6 hours
    }

    /**
     * Verify JWT with secret key, check if expired, and return username
     * @return username (subject) extracted from JWT
     */
    private fun validateToken(token: String, secretKey: SecretKey): String {
        val parsedJws = Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token)
        if (parsedJws.payload.expiration.before(Date())) {
            throw JwtException("Expired JWT")
        }
        return parsedJws.payload.subject
    }

    fun validateAccessToken(token: String): String {
        return validateToken(token, accessTokenSecretKey)
    }

    fun validateRefreshToken(token: String): String {
        return validateToken(token, refreshTokenSecretKey)
    }

    private val BEARER_PREFIX = "Bearer "

    fun getTokenFromRequest(request: HttpServletRequest): String? {
        val header = request.getHeader(HttpHeaders.AUTHORIZATION) ?: return null
        if (!header.startsWith(BEARER_PREFIX)) {
            throw JwtException("Bearer prefix not found")
        }
        return header.substring(BEARER_PREFIX.length)
    }
}
