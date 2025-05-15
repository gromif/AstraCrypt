package io.gromif.astracrypt.auth.domain.model

data class Auth(
    val type: AuthType? = null,
    val timeout: Timeout = Timeout.SECONDS_10,
    val skinType: SkinType? = null,
    val hintState: Boolean = false,
    val hintText: String? = null,
    val bindTinkAd: Boolean = false
)
