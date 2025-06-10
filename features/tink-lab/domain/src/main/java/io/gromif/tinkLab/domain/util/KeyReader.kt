package io.gromif.tinkLab.domain.util

import io.gromif.tinkLab.domain.model.result.ReadKeyResult

interface KeyReader {

    suspend operator fun invoke(
        uriString: String,
        keysetPassword: String,
        keysetAssociatedData: ByteArray
    ): ReadKeyResult
}
