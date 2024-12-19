package com.nevidimka655.astracrypt.domain.usecase.crypto

import com.nevidimka655.crypto.tink.domain.model.keyset.PrefsKeyName
import com.nevidimka655.crypto.tink.domain.usecase.encoder.HexUseCase
import com.nevidimka655.crypto.tink.domain.usecase.hash.Sha256UseCase
import com.nevidimka655.crypto.tink.domain.usecase.hash.Sha384UseCase

class PrefsKeyNameUseCase(
    private val hexUseCase: HexUseCase,
    private val sha384UseCase: Sha384UseCase
): PrefsKeyName {

    override fun get(
        tag: String,
        associatedData: ByteArray
    ): String {
        val tagBytes = tag.toByteArray()
        val prefsAliasBytes = byteArrayOf(*associatedData, *tagBytes)
        val prefsAliasHashBytes = sha384UseCase.compute(value = prefsAliasBytes)
        return hexUseCase.encode(bytes = prefsAliasHashBytes)
    }

}