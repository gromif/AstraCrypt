package io.gromif.astracrypt.auth.domain.model

data class AuthState(
    val authType: AuthType?,
    val authState: Boolean,

    val skinType: SkinType?,
    val skinState: Boolean
)
