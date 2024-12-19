package com.nevidimka655.astracrypt.domain.usecase.crypto

import com.nevidimka655.crypto.tink.domain.keyset.PrefsKeyName
import com.nevidimka655.crypto.tink.core.encoders.HexService
import com.nevidimka655.crypto.tink.core.hash.Sha384Service

class PrefsKeyNameUseCase(
    private val hexService: HexService,
    private val sha384Service: Sha384Service
): PrefsKeyName {

    override fun get(
        tag: String,
        associatedData: ByteArray
    ): String {
        val tagBytes = tag.toByteArray()
        val prefsAliasBytes = byteArrayOf(*associatedData, *tagBytes)
        val prefsAliasHashBytes = sha384Service.compute(value = prefsAliasBytes)
        return hexService.encode(bytes = prefsAliasHashBytes)
    }

}