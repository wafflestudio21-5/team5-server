package com.wafflestudio.toyproject.waffle5gramserver.user.service

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.time.LocalDateTime
import java.util.Date

data class InstagramUser(
    val id: Long,
    val username: String,
    val name: String,
    val password: String,
    val birthday: Date,
    val isPrivate: Boolean,
    val createdAt: LocalDateTime,
    val modifiedAt: LocalDateTime,
    val pronoun: String?,
    val profileImageUrl: String?,
    val bio: String?
): UserDetails {

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return mutableSetOf(SimpleGrantedAuthority("ROLE_USER"))
    }

    override fun getPassword(): String = password

    override fun getUsername(): String = username

    override fun isAccountNonExpired(): Boolean = true

    override fun isAccountNonLocked(): Boolean = true

    override fun isCredentialsNonExpired(): Boolean = true

    override fun isEnabled(): Boolean = true
}