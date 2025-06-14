package io.gromif.secure_content.data.mapper

import contract.secureContent.SecureContentContract
import io.gromif.secure_content.domain.SecureContentMode

internal fun SecureContentMode.toContractMode(): SecureContentContract.Mode = when (this) {
    SecureContentMode.DISABLED -> SecureContentContract.Mode.DISABLED
    SecureContentMode.ENABLED -> SecureContentContract.Mode.ENABLED
    SecureContentMode.FORCE -> SecureContentContract.Mode.FORCE
}