package com.wafflestudio.toyproject.waffle5gramserver.auth.oauth2

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority

class FacebookOAuth2User(
    private val attributeMap: MutableMap<String, Any>,
    override var isExistingUser: Boolean,
    override var username: String
) : CustomOAuth2User {
    override fun getProvider(): String {
        return "facebook"
    }

    override fun getId(): Long {
        return (attributeMap["id"] as String).toLong()
    }

    override fun getEmail(): String {
        return attributeMap["email"] as String
    }

    override fun getName(): String {
        return attributeMap["name"] as String
    }

    override fun getAttributes(): MutableMap<String, Any> {
        return attributeMap
    }

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return mutableSetOf(SimpleGrantedAuthority("ROLE_USER"))
    }
}
