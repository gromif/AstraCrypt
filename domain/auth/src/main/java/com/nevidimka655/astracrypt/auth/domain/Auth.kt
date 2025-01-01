package com.nevidimka655.astracrypt.auth.domain

data class Auth(
    val type: AuthType? = null,
    val skin: Skin? = null,
    val hintState: Boolean = false,
    val hintText: String? = null
)