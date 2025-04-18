package io.gromif.astracrypt.auth.presentation.settings.model

import io.gromif.astracrypt.auth.domain.model.AuthType

internal data class Params(
    val isAuthEnabled: Boolean = true,
    val typeIndex: Int = AuthType.PASSWORD.ordinal + 1,
    val skinIndex: Int = 0,
    val isAssociatedDataEncrypted: Boolean = false,
    val hintState: Boolean = true,
    val hintText: String = "Test Hint",
)
