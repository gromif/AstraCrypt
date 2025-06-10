package io.gromif.tinkLab.domain.util

import io.gromif.tinkLab.domain.model.Key

interface KeyWriter {

    operator fun invoke(
        uriString: String,
        key: Key,
        keysetPassword: String,
        keysetAssociatedData: ByteArray
    )

}