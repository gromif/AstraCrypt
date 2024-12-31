package com.nevidimka655.astracrypt.auth.domain

data class Auth(
    val type: AuthType?,
    val skin: Skin?,
    val hintState: Boolean,
    val hintText: String?
)