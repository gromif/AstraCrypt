package io.gromif.astracrypt.auth.presentation.settings.model

import io.gromif.astracrypt.auth.domain.model.AuthType
import io.gromif.astracrypt.auth.domain.model.SkinType
import io.gromif.astracrypt.auth.domain.model.Timeout

internal data class Params(
    val isAuthEnabled: Boolean = true,
    val type: AuthType? = AuthType.PASSWORD,
    val timeout: Timeout = Timeout.IMMEDIATELY,
    val skin: SkinType? = null,
    val isAssociatedDataEncrypted: Boolean = false,
    val hintState: Boolean = true,
    val hintText: String = "Test Hint",
)
