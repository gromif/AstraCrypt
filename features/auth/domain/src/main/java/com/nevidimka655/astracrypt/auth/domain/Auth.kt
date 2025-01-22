package com.nevidimka655.astracrypt.auth.domain

data class Auth(
    val type: AuthType? = null,
    val skinType: SkinType? = null,
    val hintState: Boolean = false,
    val hintText: String? = null,
    val bindTinkAd: Boolean = false
)