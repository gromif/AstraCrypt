package com.nevidimka655.astracrypt.domain.usecase.crypto

import com.nevidimka655.crypto.tink.domain.keyset.MasterKeyName
import com.nevidimka655.crypto.tink.core.encoders.HexService
import com.nevidimka655.crypto.tink.core.hash.Sha256Service

class MasterKeyNameUseCase(
    private val hexService: HexService,
    private val sha256Service: Sha256Service
): MasterKeyName {

    override suspend fun get(
        tag: String,
        associatedData: ByteArray
    ): String {
        val tagBytes = tag.toByteArray()
        val masterAliasBytes = byteArrayOf(*tagBytes, *associatedData)
        val masterAliasHashBytes = sha256Service.compute(value = masterAliasBytes)
        return hexService.encode(bytes = masterAliasHashBytes)
    }

}