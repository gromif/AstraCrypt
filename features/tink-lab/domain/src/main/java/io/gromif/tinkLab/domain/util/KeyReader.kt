package io.gromif.tinkLab.domain.util

import io.gromif.tinkLab.domain.model.Key

interface KeyReader {

    operator fun invoke(
        uriString: String,
        keysetPassword: String,
        keysetAssociatedData: ByteArray
    ): Result

    sealed interface Result {

        @JvmInline
        value class Success(val key: Key) : Result

        object Error : Result

    }

}