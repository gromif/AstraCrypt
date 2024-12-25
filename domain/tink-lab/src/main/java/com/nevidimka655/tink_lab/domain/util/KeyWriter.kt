package com.nevidimka655.tink_lab.domain.util

import com.nevidimka655.tink_lab.domain.model.Key

interface KeyWriter {

    operator fun invoke(
        uriString: String,
        key: Key,
        keysetPassword: String,
        keysetAssociatedData: ByteArray
    )

}