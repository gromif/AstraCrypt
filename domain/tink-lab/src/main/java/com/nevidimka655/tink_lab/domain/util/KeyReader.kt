package com.nevidimka655.tink_lab.domain.util

import com.nevidimka655.tink_lab.domain.model.Key

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